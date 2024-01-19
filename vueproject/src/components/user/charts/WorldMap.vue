<template>
  <div>
    <div>
      <h2 :style="{
      'text-align': 'center','margin': 'auto',
      'font-size': screenWidth * 0.0152 + 'px'
      }">
        {{workName}}&nbsp;&nbsp;{{queryMapParam.searchTime}}&nbsp;&nbsp;国际情感分布
      </h2>
    </div>
    <div style="margin-top: 1%;text-align: center" >
      <el-select class="custom-select" :size="inputSize" v-model="selectEmotion" placeholder="请选择情感" @change="updateChart">
        <el-option
          v-for="item in emotionOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-date-picker class="custom-select" :size="inputSize"
        v-model="queryMapParam.searchTime"
        align="right"
        type="month"
        placeholder="选择月份"
        value-format="yyyy-MM"
        :picker-options="pickerOptions"
        @change="getWorldMapData"
      >
      </el-date-picker>
    </div>
    <el-button class="btn my-button" type="primary" size="medium" @click="handleClick">
      查看{{label}}
    </el-button>
    <div id="worldMapChart" class="mapStyle" style="margin: auto; height: 90%;width: 100%;position: absolute;"></div>
  </div>
</template>
<script>
import worldJson from '../../../assets/map/world.json'
import {getWorldPolarityDistribute} from "../../../api/polarityAPI";
export default {
  name: "WorldMap",
  props: {
    workName: {
      type: String,
      require: true
    },
    workId: {
      type: String,
      require: true
    }
  },
  data() {
    return {
      label: "情感排名",
      inputSize: 'small',
      screenWidth: 1536,
      emotionEveryCountry: [

      ],
      selectDate: "2023-07-01",
      worldMapChart: {},
      selectEmotion: 'positive',
      emotionOptions: [
        {
          value: 'positive',
          label: '积极',
        }, {
          value: 'neutrality',
          label: '中性'
        }, {
          value: 'negative',
          label: '消极'
        }
      ],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },

      },
      dataList: [

      ],
      option: {},
      //请求参数
      queryMapParam: {
        searchWorkId: this.workId,
        searchTime: '2023-07'
      },
      //positive 从返回的json数据中提取出的积极情感值
      positiveData:[
        {
          country:null,
          value:null,
        }
      ],
      //neutrality
      neutralityData:[
        {
          country:null,
          value:null,
        }
      ],
      //negative
      negativeData:{
        country:null,
        value:null,
      },
      nameMap: {
        "Canada": "加拿大",
        "Turkmenistan": "土库曼斯坦",
        "Saint Helena": "圣赫勒拿",
        "Lao PDR": "老挝",
        "Lithuania": "立陶宛",
        "Cambodia": "柬埔寨",
        "Ethiopia": "埃塞俄比亚",
        "Faeroe Is.": "法罗群岛",
        "Swaziland": "斯威士兰",
        "Palestine": "巴勒斯坦",
        "Belize": "伯利兹",
        "Argentina": "阿根廷",
        "Bolivia": "玻利维亚",
        "Cameroon": "喀麦隆",
        "Burkina Faso": "布基纳法索",
        "Aland": "奥兰群岛",
        "Bahrain": "巴林",
        "Saudi Arabia": "沙特阿拉伯",
        "Fr. Polynesia": "法属波利尼西亚",
        "Cape Verde": "佛得角",
        "W. Sahara": "西撒哈拉",
        "Slovenia": "斯洛文尼亚",
        "Guatemala": "危地马拉",
        "Guinea": "几内亚",
        "Dem. Rep. Congo": "刚果（金）",
        "Germany": "德国",
        "Spain": "西班牙",
        "Liberia": "利比里亚",
        "Netherlands": "荷兰",
        "Jamaica": "牙买加",
        "Solomon Is.": "所罗门群岛",
        "Oman": "阿曼",
        "Tanzania": "坦桑尼亚",
        "Costa Rica": "哥斯达黎加",
        "Isle of Man": "曼岛",
        "Gabon": "加蓬",
        "Niue": "纽埃",
        "Bahamas": "巴哈马",
        "New Zealand": "新西兰",
        "Yemen": "也门",
        "Jersey": "泽西岛",
        "Pakistan": "巴基斯坦",
        "Albania": "阿尔巴尼亚",
        "Samoa": "萨摩亚",
        "Czech Rep.": "捷克",
        "United Arab Emirates": "阿拉伯联合酋长国",
        "Guam": "关岛",
        "India": "印度",
        "Azerbaijan": "阿塞拜疆",
        "N. Mariana Is.": "北马里亚纳群岛",
        "Lesotho": "莱索托",
        "Kenya": "肯尼亚",
        "Belarus": "白俄罗斯",
        "Tajikistan": "塔吉克斯坦",
        "Turkey": "土耳其",
        "Afghanistan": "阿富汗",
        "Bangladesh": "孟加拉国",
        "Mauritania": "毛里塔尼亚",
        "Dem. Rep. Korea": "朝鲜",
        "Saint Lucia": "圣卢西亚",
        "Br. Indian Ocean Ter.": "英属印度洋领地",
        "Mongolia": "蒙古",
        "France": "法国",
        "Cura?ao": "库拉索岛",
        "S. Sudan": "南苏丹",
        "Rwanda": "卢旺达",
        "Slovakia": "斯洛伐克",
        "Somalia": "索马里",
        "Peru": "秘鲁",
        "Vanuatu": "瓦努阿图",
        "Norway": "挪威",
        "Malawi": "马拉维",
        "Benin": "贝宁",
        "St. Vin. and Gren.": "圣文森特和格林纳丁斯",
        "Korea": "韩国",
        "Singapore": "新加坡",
        "Montenegro": "黑山共和国",
        "Cayman Is.": "开曼群岛",
        "Togo": "多哥",
        "China": "中国",
        "Heard I. and McDonald Is.": "赫德岛和麦克唐纳群岛",
        "Armenia": "亚美尼亚",
        "Falkland Is.": "马尔维纳斯群岛（福克兰）",
        "Ukraine": "乌克兰",
        "Ghana": "加纳",
        "Tonga": "汤加",
        "Finland": "芬兰",
        "Libya": "利比亚",
        "Dominican Rep.": "多米尼加",
        "Indonesia": "印度尼西亚",
        "Mauritius": "毛里求斯",
        "Eq. Guinea": "赤道几内亚",
        "Sweden": "瑞典",
        "Vietnam": "越南",
        "Mali": "马里",
        "Russia": "俄罗斯",
        "Bulgaria": "保加利亚",
        "United States": "美国",
        "Romania": "罗马尼亚",
        "Angola": "安哥拉",
        "Chad": "乍得",
        "South Africa": "南非",
        "Fiji": "斐济",
        "Liechtenstein": "列支敦士登",
        "Malaysia": "马来西亚",
        "Austria": "奥地利",
        "Mozambique": "莫桑比克",
        "Uganda": "乌干达",
        "Japan": "日本",
        "Niger": "尼日尔",
        "Brazil": "巴西",
        "Kuwait": "科威特",
        "Panama": "巴拿马",
        "Guyana": "圭亚那",
        "Madagascar": "马达加斯加",
        "Luxembourg": "卢森堡",
        "American Samoa": "美属萨摩亚",
        "Andorra": "安道尔",
        "Ireland": "爱尔兰",
        "Italy": "意大利",
        "Nigeria": "尼日利亚",
        "Turks and Caicos Is.": "特克斯和凯科斯群岛",
        "Ecuador": "厄瓜多尔",
        "U.S. Virgin Is.": "美属维尔京群岛",
        "Brunei": "文莱",
        "Australia": "澳大利亚",
        "Iran": "伊朗",
        "Algeria": "阿尔及利亚",
        "El Salvador": "萨尔瓦多",
        "C?te d'Ivoire": "科特迪瓦",
        "Chile": "智利",
        "Puerto Rico": "波多黎各",
        "Belgium": "比利时",
        "Thailand": "泰国",
        "Haiti": "海地",
        "Iraq": "伊拉克",
        "S?o Tomé and Principe": "圣多美和普林西比",
        "Sierra Leone": "塞拉利昂",
        "Georgia": "格鲁吉亚",
        "Denmark": "丹麦",
        "Philippines": "菲律宾",
        "S. Geo. and S. Sandw. Is.": "南乔治亚岛和南桑威奇群岛",
        "Moldova": "摩尔多瓦",
        "Morocco": "摩洛哥",
        "Namibia": "纳米比亚",
        "Malta": "马耳他",
        "Guinea-Bissau": "几内亚比绍",
        "Kiribati": "基里巴斯",
        "Switzerland": "瑞士",
        "Grenada": "格林纳达",
        "Seychelles": "塞舌尔",
        "Portugal": "葡萄牙",
        "Estonia": "爱沙尼亚",
        "Uruguay": "乌拉圭",
        "Antigua and Barb.": "安提瓜和巴布达",
        "Lebanon": "黎巴嫩",
        "Uzbekistan": "乌兹别克斯坦",
        "Tunisia": "突尼斯",
        "Djibouti": "吉布提",
        "Greenland": "格陵兰岛",
        "Timor-Leste": "东帝汶",
        "Dominica": "多米尼克",
        "Colombia": "哥伦比亚",
        "Burundi": "布隆迪",
        "Bosnia and Herz.": "波斯尼亚和黑塞哥维那",
        "Cyprus": "塞浦路斯",
        "Barbados": "巴巴多斯",
        "Qatar": "卡塔尔",
        "Palau": "帕劳",
        "Bhutan": "不丹",
        "Sudan": "苏丹",
        "Nepal": "尼泊尔",
        "Micronesia": "密克罗尼西亚",
        "Bermuda": "百慕大",
        "Suriname": "苏里南",
        "Venezuela": "委内瑞拉",
        "Israel": "以色列",
        "St. Pierre and Miquelon": "圣皮埃尔和密克隆群岛",
        "Central African Rep.": "中非",
        "Iceland": "冰岛",
        "Zambia": "赞比亚",
        "Senegal": "塞内加尔",
        "Papua New Guinea": "巴布亚新几内亚",
        "Trinidad and Tobago": "特立尼达和多巴哥",
        "Zimbabwe": "津巴布韦",
        "Jordan": "约旦",
        "Gambia": "冈比亚",
        "Kazakhstan": "哈萨克斯坦",
        "Poland": "波兰",
        "Eritrea": "厄立特里亚",
        "Kyrgyzstan": "吉尔吉斯斯坦",
        "Montserrat": "蒙特塞拉特",
        "New Caledonia": "新喀里多尼亚",
        "Macedonia": "马其顿",
        "Paraguay": "巴拉圭",
        "Latvia": "拉脱维亚",
        "Hungary": "匈牙利",
        "Syria": "叙利亚",
        "The National Flag of the Dominican Republic":"多米尼加",
        "Honduras": "洪都拉斯",
        "Myanmar": "缅甸",
        "Mexico": "墨西哥",
        "Egypt": "埃及",
        "Nicaragua": "尼加拉瓜",
        "Cuba": "古巴",
        "Serbia": "塞尔维亚",
        "Comoros": "科摩罗",
        "United Kingdom": "英国",
        "Fr. S. Antarctic Lands": "南极洲",
        "Congo": "刚果（布）",
        "Greece": "希腊",
        "Sri Lanka": "斯里兰卡",
        "Croatia": "克罗地亚",
        "Botswana": "博茨瓦纳",
        "Siachen Glacier": "锡亚琴冰川地区"
      },
      handleClick() {

      },
    }
  },
  mounted() {
    this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (this.screenWidth < 1920) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }

    this.initWorldMapChart()
    this.getWorldMapData()
    this.handleResize = ()=>{
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      this.worldMapChart.resize();
    }
    window.addEventListener('resize', this.handleResize)
  },
  methods: {
    initWorldMapChart(){
      this.worldMapChart = this.$echarts.init(document.getElementById("worldMapChart"));
      this.$echarts.registerMap('world', worldJson)
      let dataList = this.dataList;
      for(let i = 0; i < dataList.length; i++){
        dataList[i].value=0
      }
      const _this = this
      this.option = {
        tooltip: {
          //数据格式化
          formatter: function(params, callback) {
            return (
              params.name + " 情感占比<br />"+
              _this.selectEmotion+" "+params.value+"%"
            );
          },
          backgroundColor: 'rgba(19, 25, 47, 0.6)',
          borderColor: 'deepskyblue',
          textStyle: {
            color: 'white', //设置文字颜色
            fontSize: this.screenWidth * 0.01
          }
        },
        visualMap: {
          textStyle: {
            color: '#b5bb94',
            fontSize: this.screenWidth * 0.009
          },
          itemHeight: this.screenWidth * 0.092,
          itemWidth: this.screenWidth * 0.0131,
          min: 0,
          max: 100,
          left: 65,
          top: 250,

          text: ["高(百分比)", "低"], //取值范围的文字
          inRange: {
            color: [
              '#fdfdfd',
              '#f19274',
              '#ed7b57',
              '#f46032',
              '#f15221',
              '#d7281f',
              '#a50026'],
            //取值范围的颜色
          },
          show: true, //图注
        },
        geo: {
          map: 'world', //引入地图数据
          roam: true, //不开启缩放和平移
          zoom: 1.1, //视角缩放比例
          scaleLimit: {
            min: 0.5,
            max: 10
          },
          top: '10%',
          label: {
            show: false,//是否在地图上显示国家名字
            fontSize: this.screenWidth * 0.01,
            color: "rgba(201,174,174,0.7)",
          },
          nameMap: this.nameMap,
          emphasis: {
            borderColor: "rgba(0, 0, 0, 0.2)",
            itemStyle:{ //高亮的显示设置
              areaColor: "skyblue", //鼠标选择区域颜色
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 25,
              borderWidth: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
          itemStyle: {
            shadowColor: "#5d5d5d",
            shadowBlur: 10,
            // shadowOffsetX: 1,
            // shadowOffsetY: 2
          }
        },
        // 鼠标悬浮提示框
        series: [
          {
            name: "国家",
            type: "map",
            id: 'dataDistribute',
            geoIndex: 0,
            data: this.dataList,
            animationDelay: 2000,
            animationDurationUpdate: 1000,
            universalTransition: true,
            animationDuration: 2000,
            animationEasing: 4000,
          },
        ],
      }
      this.currentOption = this.option;
    },
    //获取数据
    getWorldMapData(){
      getWorldPolarityDistribute(this.queryMapParam).then((res)=>{
        if (res.code === "0") {
          //提取各情感值原始数据
          this.positiveData = res.data.map((item) => {
            return {country: item.country, value: item.positive}
          });
          // console.log(res.data)
          this.neutralityData = res.data.map((item) => {
            return {country: item.country, value: item.neutrality}
          });
          this.negativeData = res.data.map((item) => {
            return {country: item.country, value: item.negative}
          });
          this.positiveData.sort(function (a, b) {
            return a.value - b.value;
          });
          this.neutralityData.sort(function (a, b) {
            return a.value - b.value;
          });
          this.negativeData.sort(function (a, b) {
            return a.value - b.value;
          });
          //计算各国情感占比
          this.positiveData = this.calProportion(this.positiveData)
          this.negativeData = this.calProportion(this.negativeData)
          this.neutralityData = this.calProportion(this.neutralityData)
          //根据所选情感值 将对应数组中的数据填充至worldMap中 更新图表
          this.updateChart();
          this.initBarOption()

          if (this.label === "情感排名") {
            this.worldMapChart.setOption(this.option);
          } else {
            this.worldMapChart.setOption(this.barOption)
          }

          this.handleClick = ()=>{
            this.currentOption = this.currentOption === this.option ? this.barOption : this.option;
            this.worldMapChart.setOption(this.currentOption, true);
            if (this.label === "情感排名") {
              this.label = "情感全球分布"
            } else {
              this.label = "情感排名"
            }
          }
          this.handleResize = ()=>{
            this.worldMapChart.resize()
          }
        }
      })

   },
    //计算各国情感占比
    calProportion(emotionArray){
      let sum = 0
      emotionArray.forEach(item => {
          sum += item.value;
      })
      emotionArray.forEach(item => {
        item.value = Math.round(item.value/sum*10000)/100;
      })
      // console.log(emotionArray)
      return emotionArray
    },
    //填充worldMap的数据
    fillWorldMapData(){
      switch (this.selectEmotion){
        case 'positive':
          this.emotionEveryCountry = this.positiveData;
          break;
        case 'negative':
          this.emotionEveryCountry = this.negativeData;
          break;
        case 'neutrality':
          this.emotionEveryCountry = this.neutralityData;
          break;
      }
    },
    //更新图表
    updateChart(){
      // console.log("1111")
      this.fillWorldMapData()
      // console.log(this.emotionEveryCountry)
      let countryEmotion = {}
      let countryKeys = this.emotionEveryCountry.map((item) => {return item.country})//取出国家
      // console.log(countryKeys)
      let emotionValues = this.emotionEveryCountry.map((item) => {return item.value})//取出情感值
      // console.log(emotionValues)
      //将countryKeys和emotionValues数组合并成一个键值对数组
      for (let i = 0; i < countryKeys.length; i++) {
        countryEmotion[countryKeys[i]] = emotionValues[i]
      }
      // console.log(countryEmotion)
      // console.log(countryEmotion['美国'])
      for (let i = 0; i < this.dataList.length; i++) {
        if (countryEmotion[this.dataList[i].name]!=null){
          // console.log("5555")
          this.dataList[i].value = countryEmotion[this.dataList[i].name]
          // console.log( this.dataList[i].value)
        }else {
          this.dataList[i].value=0
        }
      }
      this.initBarOption()
      if (this.label === "情感排名") {
        this.worldMapChart.setOption(this.option);
      } else {
        this.worldMapChart.setOption(this.barOption)
      }
    },
    //对数组进行去重
    duplicate(emotionArray){
      let obj = {};
      emotionArray.forEach(item => {
        if (obj.hasOwnProperty(item.country)) {
          obj[item.country] += item.value
        } else {
          obj[item.country] = item.value
        }
      })
      //获取键和值 得到国家和情感值数组
      let countryArr = Object.keys(obj)
      let valueArr = Object.values(obj)
      //将countryArr和valueArr合并成一个数组对象
      let newArr = countryArr.map((item, index) => {
        return {
          country: item,
          value: valueArr[index]
        }
      })
      return newArr
    },
    async initBarOption() {
      this.barOption = {
        tooltip: {
          show: true,
          trigger: "item",
          triggerOn: "click",
          formatter: '{b}: {c}%'
        },
        grid: {
          left: '20%',
          top: '3%',
          right: '8%',
          bottom: '3%',
          width: '60%',
          height: '90%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          axisLabel: {
            color: '#26c6e1',
            fontSize: 15,
            fontWeight: 'bold'
          }
        },
        yAxis: {
          type: 'category',
          axisLabel: {
            rotate: 30,
            color: '#dc5a15',
            fontSize: 15,
            fontWeight: 'bold',
            textBorderWidth: 1.5,
            textBorderColor: '#000'
          },
          data: this.emotionEveryCountry.map(function (item) {
            return item.country;
          })
        },
        animationDurationUpdate: 1500,
        series: {
          type: 'bar',
          id: 'dataDistribute',
          data: this.emotionEveryCountry.map(function (item) {
            return item.value;
          }),
          barMaxWidth: 40, // 柱子的最大宽度
          universalTransition: true,
          itemStyle: {
            borderRadius: 30,
            color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#14c8d4' },
              { offset: 1, color: '#43eec6' }
            ])
          },
          label: {
            show: true,
            position: 'right',
            valueAnimation: true,
            color: '#26c6e1',
            fontSize: 15,
            fontWeight: 'bold',
            textBorderWidth: 1.5,
            textBorderColor: '#000',
            formatter: '{c}%'
          }
        }
      };
    }
  },
  beforeMount() {
    // this.workName = this.$route.query.workName
    // console.log(this.workName)
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
  created() {
    Object.values(this.nameMap).forEach((key)=>{
      this.dataList.push({name: key, value: 0})
    })
  },
}
</script>
<style scoped>
@import "../../../assets/styles/mystyle.css";
</style>
