<template>
  <div>
    <el-row align="center" justify="center" style="display: flex; justify-content: center">
      <el-col :span="16" :xs="24" :md="16" align="center">
        <el-card class="box-card grid-content" shadow="always">
          <h2 style="text-align: center" >{{country}} </h2>
          <div id="assessChart" style="width: 100%;height: 800px; margin-top: 20px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {queryPolarity} from "../../../api/polarityAPI";
export default {
  name: "AssessmentDetailChart",
  data() {
    return {
      country: null,
      option: {
        legend: {
          left: 'center',
          data: ['积极情感', '消极情感', '中性情感']
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        grid: {
          top: '4%',
          left: '1%',
          right: '3%',
          bottom: '6%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['2023-01', '2023-02', '2023-03', '2023-04','2023-05','2023-06','2023-07','2023-08']
          }
        ],
        yAxis: [
          {
            type: 'value',
            min: 0,
            max: 5000,
            nameTextStyle: {
              fontWeight: 600,
              fontSize: 12,
              fontFamily: 'Times New Roman'
            }
          }
        ],
        series: [
          {
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            smooth: true,
            emphasis: {
              focus: 'series'
            },
            name: '积极情感',
            data: []
          },
          {
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            smooth: true,
            emphasis: {
              focus: 'series'
            },
            name: '消极情感',
            data: []
          },
          {
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            smooth: true,
            emphasis: {
              focus: 'series'
            },
            name: '中性情感',
            data: []
          },
          /*{
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {
              focus: 'self'
            },
            label: {
              formatter: '{b}: {@2012} ({d}%)'
            },
            encode: {
              itemName: 'product',
              value: '2012',
              tooltip: '2012'
            }
          }*/
        ]
      },
      queryAssessmentParam: {
        searchCountry: this.country
      },
      positiveEmotion: {
        date:null,
        emotionValue:null
      },
      negativeEmotion: {
        date:null,
        emotionValue:null
      },
      neutralityEmotion:{
        date:null,
        emotionValue:null
      }
    }
  },
  beforeMount() {
    this.country = this.$route.query.country // 接收路由传递参数
    console.log(this.country)
    if (!this.country) {
      this.country = '美国'
    }
    this.queryAssessmentParam.searchCountry = this.country
    if (this.country === "全球") {
      this.queryAssessmentParam.searchCountry = "" // 查询全部的
    }
  },
  mounted() {
    this.initAssessChart()
    this.getAssessData()
  },
  methods: {
    initAssessChart() {//初始化图表
      this.assessChart = this.$echarts.init(document.getElementById('assessChart'))
      // this.assessChart.setOption(this.option);
      window.addEventListener("resize", () => {
        this.assessChart.resize();
      });
    },
    //调用接口 获取极性情感数据
    getAssessData() {
      queryPolarity(this.queryAssessmentParam).then((res) => {
        if (res.code === "0") {
            //提取出原始积极情感数据并去重
            this.positiveEmotion = res.data.map((item) => {
              return {date: item.time.substring(0,7), emotionValue: item.positive}//日期只提取年月
            });
            this.positiveEmotion = this.duplicate(this.positiveEmotion)
            console.log(this.positiveEmotion)
            //获取图表所需积极情感数据
            this.option.series[0].data = this.positiveEmotion.map((item) => {
              return item.emotionValue
            })
            //提取出原始消极情感数据并去重
            this.negativeEmotion = res.data.map((item) => {
              return {date: item.time.substring(0,7), emotionValue: item.negative}
            });
            this.negativeEmotion = this.duplicate(this.negativeEmotion)
            console.log(this.negativeEmotion)
            //获取图表所需消极情感数据
            this.option.series[1].data = this.negativeEmotion.map((item) => {
              return item.emotionValue
            })
            //提取出原始中性情感数据并去重
            this.neutralityEmotion = res.data.map((item) => {
              return {date: item.time.substring(0,7),emotionValue: item.neutrality}
            });
            this.neutralityEmotion = this.duplicate(this.neutralityEmotion)
            //获取图表所需中性情感数据
            this.option.series[2].data = this.neutralityEmotion.map((item) => {
              return item.emotionValue
            })
            this.updateAssessChart()
          }
      })
    },
    //对数组进行去重
    duplicate(emotionArray){
      let obj = {};
      emotionArray.forEach(item => {
        if (obj.hasOwnProperty(item.date)) {
          obj[item.date] += item.emotionValue
        } else {
          obj[item.date] = item.emotionValue
        }
      })
      //获取键和值 得到日期和情感值数组
      let dateArr = Object.keys(obj)
      let emotionValueArr = Object.values(obj)
      //将dateArr和emotionValueArr合并成一个数组对象
      let newArr = dateArr.map((item, index) => {
        return {
          date: item,
          emotionValue: emotionValueArr[index]
        }
      })
      return newArr
    },
    //更新图表
    updateAssessChart() {
      this.assessChart.setOption(this.option);
      /*let _this = this
      setTimeout(function () {
        _this.assessChart.on('updateAxisPointer', function (event) {
          const xAxisInfo = event.axesInfo[0];
          // console.log(xAxisInfo)
          if (xAxisInfo) {
            const dimension = xAxisInfo.value + 1;
            _this.assessChart.setOption({
              series: {
                id: 'pie',
                label: {
                  formatter: '{b}: {@[' + dimension + ']} ({d}%)'
                },
                encode: {
                  value: dimension,
                  tooltip: dimension
                }
              }
            });
          }
        });
        _this.assessChart.setOption(_this.option)
      })*/
    }
  }
}
</script>
<style scoped>
.box-card {
  background-color: #ebf2f3;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
