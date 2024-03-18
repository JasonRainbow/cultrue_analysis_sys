<template>
  <div id="main_div" style="height: 100%;width:100%">
    <div style="text-align:center;height:10%;margin-top: 5%">
<!--      <div :class="{'border-font': height === '300px'}"-->
<!--        :style="{-->
<!--        'margin-bottom': divWidth * 0.005 + 'px',-->
<!--        marginTop: '-3px',-->
<!--        'font-size': divWidth * 0.038 + 'px'-->
<!--      }">-->
<!--        {{selectCountry}}&nbsp;{{selectDate}}词云图-->
<!--      </div>-->
      <el-select class="custom-select3" :size="inputSize" style="width: 28%" v-model="selectCountry" placeholder="请选择国家" @change="getWordData">
        <el-option
          v-for="item in countryOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-select class="custom-select3" :size="inputSize" style="width: 28%" v-model="queryWordFreqParam.searchPlatform" placeholder="请选择平台" @change="getWordData">
        <el-option
          v-for="item in platFormOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-date-picker class="custom-select3" style="width: 28%"
        :size="inputSize"
        v-model="selectDate"
        align="right"
        type="date"
        placeholder="选择日期"
        :picker-options="pickerOptions"
        value-format="yyyy-MM-dd"
        @change="getWordData">
      </el-date-picker>
    </div>
    <div id="wordCloud" :style="{height: height2, width: width, marginTop: marginTop}"></div>
  </div>
</template>

<script>
import {getWordFreqRes} from "../../../api/word_freq_anaAPI";
import 'echarts-wordcloud'
import {getCountries} from "../../../api/commentAPI";
export default {
  name: "WordCloud",
  props: {//声明 用于父组件和子组件通信
    workId: {//监测作品编号
      type: Number,
      require: true
    },
    height: {
      type: String,
      require: false,
      default: '300px'
    },
    width: {
      type: String,
      require: false,
      default: '100%'
    },
    marginTop: {
      type: String,
      require: false,
      default: '3px'
    },
    mask: {
      type: Boolean,
      require: false,
      default: false
    },
    numLimit: {
      type: Number,
      require: false,
      default: 80
    }
  },
  computed: {
    height2() {
      return this.height === '300px'? '70%': this.height
    }
  },
  data() {
    return {
      divWidth: 475,
      inputSize: 'mini',
      wordCloudChart:null,
      selectCountry:"全球",
      // selectDate:"2023-07-01",
      selectDate: "",
      selectPlatForm:"豆瓣",
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      countryOptions:[
        {
          value: '全球',
          label: '全球',
        }, {
          value: '美国',
          label: '美国'
        }, {
          value: '英国',
          label: '英国'
        }, {
          value: '法国',
          label: '法国'
        },{
          value: '德国',
          label: '德国'
        }
      ],
      platFormOptions:[
        {
          value: 'Youtube',
          label: 'Youtube',
        }, {
          value: 'Twitter',
          label: 'Twitter'
        }, {
          value: 'Facebook',
          label: 'Facebook'
        }, {
          value: 'IMDb',
          label: 'IMDb'
        }, {
          value: 'Amazon',
          label: 'Amazon'
        }, {
          value: 'GoodReads',
          label: 'GoodReads'
        }, {
          value: '豆瓣',
          label: '豆瓣'
        }, {
          value: '烂番茄',
          label: '烂番茄'
        }
      ],
      maskImage: null,
      wordCloud:{},
      //词云配置
      wordCloudOption:{
        series: [
          {
            type: 'wordCloud',
            shape: 'square',
            keepAspect: false,
            left: 'center',
            top: 'center',
            gridSize: 8,
            sizeRange: [25, 60],
            rotationRange: [-90, 90], // 设置文字的旋转
            rotationStep: 45,
            drawOutOfBound: false,
            shrinkToFit: false, // 是否缩小一些文字
            layoutAnimation: true,
            textStyle: {
              fontFamily: 'sans-serif',
              fontWeight: 'bold',
              color: function () {
                return 'rgb(' + [Math.round(Math.random() * 160),
                  Math.round(Math.random() * 160),
                  Math.round(Math.random() * 160)].join(',') + ')';
              }
            },
            emphasis:{
              focus: 'self',
              textStyle: {textShadowBlur: 6,textShadowColor: '#333'}
            },
            width: '100%',
            height: '100%',
            //数据
            data: [
              { name: '流浪地球', value: 4.5 },
              { name: '人类', value: 3.8 },
              { name: '文明', value: 3 },
              { name: '科幻', value: 2.5 },
              { name: '世界', value: 2.3 }
            ]
          }
        ]
      },
      //请求参数 键值对
      queryWordFreqParam:{
        searchWorkId:this.workId,
        searchTime: this.selectDate,
        searchCountry: this.selectCountry,
        searchPlatform:"豆瓣",
        numLimit: this.numLimit
      },
      handleResize: null,
    }
  },
  methods: {
    getAllCountries() {
      getCountries({workId: this.workId}).then((res)=>{
        // alert("hello");
        this.countryOptions = [{label: "全球", value: "全球"}]
        this.countryOptions = this.countryOptions.concat(res.data.map((item)=>{
          return {label: item, value: item}
        }))
        if (this.countryOptions.length > 1) {
          this.selectCountry = this.countryOptions[1].value;
        }
      })
    },
    initWordCloud() {
      this.wordCloudChart = this.$echarts.init(document.getElementById('wordCloud'))
      this.wordCloudChart.setOption(this.wordCloudOption)
      //监听窗口大小变化 做到响应，在beforeDestroy中清除
      // window.addEventListener("resize", () => {this.wordCloudChart.resize()})
    },
    getWordData(){//获取数据
      if (this.selectCountry === "全球") {
        this.queryWordFreqParam.searchCountry = ""
      } else {
        this.queryWordFreqParam.searchCountry = this.selectCountry
      }
      this.queryWordFreqParam.searchTime = this.selectDate
      // console.log(this.queryWordFreqParam)
      getWordFreqRes(this.queryWordFreqParam).then((res) => {
        if (res.code === "0") {
          // console.log(res.data.keywords)
          this.wordCloudOption.series[0].data = res.data.keywords.map((item) => {
            return {name: item.word, value: item.counts}
          })
          // console.log(this.wordCloudOption.series[0].data)
          this.updateWordCloud();
        }
      })
    },
    updateWordCloud(){
      this.wordCloudChart.setOption(this.wordCloudOption)
    }
  },
  async mounted() {
    if (this.mask) {
      this.maskImage = new Image()
      this.maskImage.src = require('../../../assets/img/mask.jpg')
      this.wordCloudOption.series[0].maskImage = this.maskImage
    }
    this.divWidth = document.getElementById("main_div").clientWidth
    if (this.divWidth < 500) {
      this.inputSize = "mini"
    } else if (this.divWidth < 570) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }
    await this.getAllCountries()
    this.getWordData()
    this.initWordCloud()
    this.handleResize = ()=>{
      this.divWidth = document.getElementById("main_div").clientWidth
      this.wordCloudChart.resize();
    }
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  }
}
</script>

<style scoped>
@import "../../../assets/styles/mystyle.css";

#wordCloud {
  margin: auto auto;
}

</style>
