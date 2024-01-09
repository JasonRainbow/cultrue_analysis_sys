<template>
    <div style="text-align:center;margin: 30px auto; height: 100%; width: 90%">
      <h2>近12个月 极性情感变化趋势</h2>
      <div style="margin-top: 15px; width: 100%" >
<!--        <el-select class="custom-select" v-model="selectCountry" placeholder="国家" @change="this.getData">-->
<!--          <el-option-->
<!--            v-for="item in countryOptions"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--        <el-date-picker-->
<!--          class="custom-select2"-->
<!--          v-model="selectYear"-->
<!--          type="year"-->
<!--          :clearable="false"-->
<!--          value-format="yyyy"-->
<!--          @change="this.getData"-->
<!--          :picker-options="pickerOptions"-->
<!--          placeholder="选择年">-->
<!--        </el-date-picker>-->
      </div>
      <div id="myChart" :style="{margin: '10px auto', width: width, height: height2}"></div>
    </div>
</template>

<script>
import {getYearPolarity} from "../../../api/polarityAPI";
import {getCountries} from "../../../api/commentAPI";
import {polarityCountMonthInterval} from "../../../api/polarityAPI";
export default {
  name: "PolarityLineChart",
  props: {//用于组件通信
    workId: {
      type: Number,
      require: true
    },
    country:{
      type:String,
      require:true
    },
    width: {
      type: String,
      require: false,
      default: "1000px"
    },
    height: {
      type: String,
      require: false,
      default: "500px"
    },
  },
  computed:{
    height2(){
      return this.height !== "400px" ? this.height : "400px"
    }
  },
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        }
      },
      option: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['积极情感', '消极情感', '中性情感'],
          textStyle: {
            color: "#e2652f",
            fontSize: 15
          }
        },
        grid: {
          left: '3%',
          right: '8%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            name: '日期',
            boundaryGap: false,
            nameTextStyle: {
              fontWeight: "bold",
              fontSize: 14,
              fontFamily: 'Times New Roman'
            },
            data: ['1', '2', '3', '4', '5', '6', '7']
          }
        ],
        yAxis: [
          {
            type: 'value',
            min: 0,
            max: 100,
            name: '占比%',
            nameTextStyle: {
              fontWeight: 600,
              fontSize: 15,
              fontFamily: 'Times New Roman'
            }
          }
        ],
        series: [
          {
            name: '积极情感',
            type: 'line',
            smooth:true,
            emphasis: {
              focus: 'series'
            },
            areaStyle: {
              color: '#6666FF',
              opacity: 0.5
            },
            data: [30, 40, 35, 30, 50, 20, 15]
          },
          {
            name: '消极情感',
            type: 'line',
            smooth:true,
            areaStyle: {
              color: '#32CD32',
              opacity: 0.5
            },
            emphasis: {
              focus: 'series'
            },
            data: [20, 15, 25, 14, 34, 25, 10]
          },
          {
            name: '中性情感',
            type: 'line',
            smooth:true,
            emphasis: {
              focus: 'series'
            },
            areaStyle: {
              color: '#FFD700',
              opacity: 0.5
            },
            data: [50, 45, 40, 56, 16, 55, 75]
          }
        ]
      },
      myChart: {},
      countryOptions:[
        {
          value: '全球',
          label: '全球',
        }, {
          value: '美国',
          label: '美国'
        }, {
          value: '中国',
          label: '中国'
        }, {
          value: '日本',
          label: '日本'
        }, {
          value: '法国',
          label: '法国'
        },{
          value: '加拿大',
          label: '加拿大'
        },
        {
          value: '印度',
          label: '印度'
        },
        {
          value: '俄罗斯',
          label: '俄罗斯'
        },
        {
          value: '巴西',
          label: '巴西'
        },
        {
          value: '德国',
          label: '德国'
        }
      ],
      selectCountry: '全球',
      // selectMonth: '2023-07',
      selectYear: '2023',
      countryEmo2: {},
      queryPolarityParams: {
        searchCountry:this.country
      },
    }
  },
  async mounted() {
    await this.getAllCountries()
    this.getData()
    this.initLineChart()
    // console.log("line chart")
    let width = document.getElementById("myChart").clientWidth
    let height = document.getElementById("myChart").clientHeight
      window.addEventListener('resize',  ()=> {
      this.myChart.resize();
    })
    window.onload = ()=>{
      this.myChart.resize()
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
      })
    },
    initLineChart() {
      this.myChart = this.$echarts.init(document.getElementById('myChart'))
      this.myChart.setOption(this.option)
    },
    getData() {//获取数据
      //调用接口
      if(this.workId !== 0){
        this.queryPolarityParams.workId = this.workId
      }
      this.queryPolarityParams.searchCountry=this.country
      /*polarityCountDayInterval(this.queryPolarityParams).then((res) => {
        if (res.code === "0") {
          // console.log(res.data)
          this.option.xAxis[0].data = res.data.statisticsInfo.map((item) => {
            return item.postTime
          })
          // console.log(this.option.xAxis[0].data)
          this.option.series[0].data = res.data.statisticsInfo.map((item) => {
            if (item.positive + item.negative + item.neutrality === 0) return 0;
            return (item.positive / (item.positive + item.negative + item.neutrality) * 100).toFixed(2)
          })
          this.option.series[1].data = res.data.statisticsInfo.map((item) => {
            if (item.positive + item.negative + item.neutrality === 0) return 0;
            return (item.negative / (item.positive + item.negative + item.neutrality) * 100).toFixed(2)
          })
          this.option.series[2].data = res.data.statisticsInfo.map((item) => {
            if (item.positive + item.negative + item.neutrality === 0) return 0;
            return (item.neutrality / (item.positive + item.negative + item.neutrality) * 100).toFixed(2)
          })
          this.updateChart();
        }
      })*/
      // console.log(this.queryPolarityParams,"params")
      polarityCountMonthInterval(this.queryPolarityParams).then((res) => {
        if (res.code === "0") {
          // console.log(this.queryPolarityParams,"params2")
          // console.log(res.data,'res')
          this.option.xAxis[0].data = res.data.map((item) => {
            return item.postTime
            // return item.postTime.substring(0, 7)
          })
          // console.log(this.option.xAxis[0].data)
          this.option.series[0].data = res.data.map((item) => {
            if (item.positive + item.negative + item.neutrality === 0) return 0;
            // console.log(item.positive, item.negative, item.neutrality)
            return (item.positive / (item.positive + item.negative + item.neutrality) * 100).toFixed(2)
          })
          this.option.series[1].data = res.data.map((item) => {
            if (item.positive + item.negative + item.neutrality === 0) return 0;
            return (item.negative / (item.positive + item.negative + item.neutrality) * 100).toFixed(2)
          })
          this.option.series[2].data = res.data.map((item) => {
            if (item.positive + item.negative + item.neutrality === 0) return 0;
            return (item.neutrality / (item.positive + item.negative + item.neutrality) * 100).toFixed(2)
          })
          this.updateChart();
        }
      })
    },
    updateChart() {
      this.myChart.setOption(this.option);
    }
  }
}
</script>

<style lang="css" scoped>

@import "../../../assets/styles/mystyle.css";

.custom-select /deep/ .el-input__inner {
  font-size: 17px;
}

.custom-select2 /deep/ .el-input__inner {
  font-size: 16px;
}

.custom-select2 /deep/ .el-input__icon {
  font-size: 17px;
}

.custom-select /deep/ .el-select__caret {
  font-size: 17px;
}

</style>
