<template>
  <div>
    <div style="margin-top: 20px; text-align: center;">
      <el-button class="btn my-button" type="primary" size="medium" @click="handleClick">
        查看{{label}}
      </el-button>
      <div class="title">数据全球分布</div>
    </div>
    <div id="dataDistribute" style="width: 100%"></div>
  </div>
</template>

<script>
import worldJson from "../../../assets/map/world.json"
import {getCountryCommentsNumByWorkId} from "../../../api/commentAPI";
export default {
  name: "DataDistributeMap",
  data() {
    return {
      label: "数据排行",
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
      data: [

      ],
      data2: [
        {
          name: "俄罗斯",
          value: 100
        },
        {
          name: "中国",
          value: 800
        },
        {
          name: "美国",
          value: 600
        },
        {
          name: "德国",
          value: 130
        },
        {
          name: "英国",
          value: 140
        },
        {
          name: "法国",
          value: 110
        },
      ],
      handleClick() {

      }
    }
  },
  methods: {
    updateChart() {
      let myChart = this.$echarts.init(document.getElementById("dataDistribute"))
      this.$echarts.registerMap("world", worldJson)
      const _this = this
      const mapOption = {
        tooltip: {
          //数据格式化
          formatter: function(params, callback) {
            return (
              params.name + " 评论总量：" + params.value
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
          max: 1300,
          left: 65,
          top: 365,

          text: ["高", "低"], //取值范围的文字
          inRange: {
            color: [
              '#e0f3f8',
              '#abd9e9',
              '#74add1',
              '#4575b4',
              '#313695',
              '#ffffbf',
              '#fee090',
              '#fdae61',
              '#f46d43',
              '#d73027',
              '#a50026'],
            //取值范围的颜色
          },
          show: true, //图注
        },
        geo: {
          map: 'world', //引入地图数据
          roam: true, //不开启缩放和平移
          zoom: 1.1, //视角缩放比例
          label: {
            show: false,//是否在地图上显示国家名字
            fontSize: 15,
            color: "rgba(201,174,174,0.7)",
          },
          scaleLimit: {
            min: 0.5,
            max: 10
          },
          nameMap: this.nameMap,
          emphasis: {
            borderColor: "rgba(0, 0, 0, 0.2)",
            itemStyle:{ //高亮的显示设置
              areaColor: "skyblue", //鼠标选择区域颜色
              // shadowOffsetX: 0,
              // shadowOffsetY: 0,
              // shadowBlur: 20,
              // borderWidth: 0,
              // shadowColor: "rgba(0, 0, 0, 0.5)",
            },
            label: {
              color: '#000000',
              fontSize: 15
            }
          },
          itemStyle: {
            shadowColor: "#1c6189",
            shadowBlur: 8,
            // shadowOffsetX: 1,
            // shadowOffsetY: 2
          }
        },
        // 鼠标悬浮提示框
        series: [
          {
            id: "dataDistribute",
            top: 0,
            name: "数据分布地图",
            type: "map",
            geoIndex: 0,
            data: this.data,
            animationDelay: 2000,
            animationDurationUpdate: 1000,
            universalTransition: true,
            animationDuration: 2000,
            animationEasing: 4000,
          },
        ],
      };
      const barOption = {
        tooltip: {
          show: true,
          trigger: "item",
          triggerOn: "click",
          formatter: '{b}: {c}'
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          axisLabel: {
            rotate: 30
          },
          data: this.data2.map(function (item) {
            return item.name;
          })
        },
        animationDurationUpdate: 1000,
        series: {
          type: 'bar',
          id: 'dataDistribute',
          data: this.data2.map(function (item) {
            return item.value;
          }),
          universalTransition: true
        }
      };
      let currentOption = mapOption;
      myChart.setOption(mapOption);
      this.handleClick = ()=>{
        currentOption = currentOption === mapOption ? barOption : mapOption;
        myChart.setOption(currentOption, true);
        if (this.label === "数据排行") {
          this.label = "全球分布"
        } else {
          this.label = "数据排行"
        }
      }
      this.handleResize = ()=>{
        myChart.resize()
      }
      window.addEventListener("resize", this.handleResize)
    },
    getData() {
      getCountryCommentsNumByWorkId().then((res)=>{
        if (res.code === "0") {
          this.data2 = []
          let mp = {}
          res.data.forEach((item)=>{
            if (item["cnt"] > 20) {
              this.data2.push({
                name: item["country"],
                value: item["cnt"]
              })
            }
            mp[item["country"]] = item["cnt"]
          })
          // console.log(this.data2)
          for (let i = 0; i < this.data.length; i++) {
            if (mp[this.data[i].name] != null) {
              this.data[i].value =  mp[this.data[i].name]
            }
          }
          this.updateChart()
        }
      })
    },
    initChart() {
      this.getData()
    }
  },
  created() {
    Object.values(this.nameMap).forEach((key)=>{
      this.data.push({name: key, value: 0})
    })
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
}
</script>

<style scoped lang="css">

@import "../../../assets/styles/mystyle.css";

#dataDistribute {
  width: 95%;
  height: 630px;
}

.title {
  display: inline-block;
  font-size: 26px;
}

.btn {
  float: left;
}

</style>
