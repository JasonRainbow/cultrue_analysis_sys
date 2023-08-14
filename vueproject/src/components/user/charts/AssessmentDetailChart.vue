<template>
  <div>
    <el-row align="center" justify="center" style="display: flex; justify-content: center">
      <el-col :span="14" :xs="24" :md="13" align="center">
        <el-card class="box-card grid-content" shadow="always">
          <div style="text-align: left">
            <router-link to="/effect">
              <el-link type="warning" style="font-size: 18px;">返回</el-link>
            </router-link>
          </div>
          <h2 style="text-align: center" >{{country}} </h2>
          <div id="assessChart" style="width: 100%;height: 800px; margin-top: 20px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {polarityCountMonthInterval} from "../../../api/polarityAPI";

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
        dataset: {
          source: [
            ['time', '2022-09', '2022-10', '2022-11', '2022-12', '2023-01', '2023-02', '2023-03', '2023-04','2023-05','2023-06','2023-07','2023-08'],
            ['积极情感', 56.5, 82.1, 88.7, 70.1, 53.4, 85.1, 56.5, 82.1, 88.7, 70.1, 53.4, 85.1],
            ['消极情感', 51.1, 51.4, 55.1, 53.3, 73.8, 68.7, 51.1, 51.4, 55.1, 53.3, 73.8, 68.7],
            ['中性情感', 40.1, 62.2, 69.5, 36.4, 45.2, 32.5, 40.1, 62.2, 69.5, 36.4, 45.2, 32.5],
          ]
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          },
          showContent: true,
          borderColor: '#2381bb',
        },
        grid: {
          top: '50%',
          left: '1%',
          right: '4%',
          bottom: '6%',
          containLabel: true,
          show: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
          }
        ],
        yAxis: [
          {
            type: 'value',
            min: 0,
            // max: 3800,
            nameTextStyle: {
              fontWeight: 500,
              fontSize: 12,
              fontFamily: 'Times New Roman'
            },
            gridIndex: 0
          }
        ],
        series: [
          {
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {
              focus: 'series'
            },
            name: '积极情感',
          },
          {
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {
              focus: 'series'
            },
            name: '消极情感',
          },
          {
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            seriesLayoutBy: 'row',
            smooth: true,
            emphasis: {
              focus: 'series'
            },
            name: '中性情感',
          },
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {
              focus: 'self'
            },
            label: {
              formatter: '{b}: {@2022-09} ({d}%)',
              textStyle : {
                color: "#844400",
                fontSize : 14
              }
            },
            encode: {
              itemName: 'time',
              value: '2022-09',
              // tooltip: '2022-10'
            },
          }
        ]
      },
      queryAssessmentParam: {
        searchCountry: this.country,
        offset: 12
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
    // console.log(this.country)
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
      // console.log(this.queryAssessmentParam)
      // 改进的方法  直接从后台获取数据
      polarityCountMonthInterval(this.queryAssessmentParam).then((res)=>{
        if (res.code === "0") {
          let res_data = res.data
          // console.log(res_data)
          // 填入时间数据
          let time_data = ["time"]
          time_data = time_data.concat(res_data.map((item)=>{
            return item.postTime
          }))
          this.option.dataset.source[0] = time_data
          // 填入积极情感数据
          let positive_data = ["积极情感"]
          positive_data = positive_data.concat(res_data.map((item)=>{
            return item.positive
          }))
          this.option.dataset.source[1] = positive_data
          // 填入消极情感数据
          let negative_data = ["消极情感"]
          negative_data = negative_data.concat(res_data.map((item)=>{
            return item.negative
          }))
          this.option.dataset.source[2] = negative_data
          // 填入中立情感数据
          let neutrality_data = ["中性情感"]
          neutrality_data = neutrality_data.concat(res_data.map((item)=>{
            return item.neutrality
          }))
          this.option.dataset.source[3] = neutrality_data
          this.option.series[3].label.formatter = '{b}: {@'+time_data[1]+'} ({d}%)'
          this.option.series[3].encode.value = time_data[1]
          // console.log(time_data[1])
          // console.log(this.option.dataset.source)
          this.updateAssessChart()
        }
      })
      /*queryPolarity(this.queryAssessmentParam).then((res) => {
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
          console.log(this.option.series[2].data)
            this.updateAssessChart()
          }
      })*/
    },
    /*//对数组进行去重
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
    },*/
    //更新图表
    updateAssessChart() {
      let _this = this
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
                },

              }
            });
          }
        });
        _this.assessChart.setOption(_this.option)
      })
      this.assessChart.setOption(this.option);
    }
  }
}
</script>
<style scoped>
.box-card {
  background-color: #dde5f3;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
