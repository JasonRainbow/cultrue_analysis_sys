<template>
    <div style="text-align:center;margin-top: 30px">
      <h2>{{ selectCountry }}&nbsp;{{selectMonth}} 极性情感趋势</h2>
      <div style="margin-top: 15px;" >
        <el-select v-model="selectCountry" placeholder="国家" @change="this.getData">
          <el-option
            v-for="item in countryOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-date-picker
          v-model="selectMonth"
          type="month"
          :clearable="false"
          value-format="yyyy-MM"
          @change="this.getData"
          placeholder="选择月">
        </el-date-picker>
        <div style="">
          <div id="myChart" style="margin-top: 10px;width: 600px; height: 400px"></div>
        </div>
      </div>
    </div>
</template>

<script>
import {polarityCountDayInterval} from "../../../api/polarityAPI";
import {getCountries} from "../../../api/commentAPI";
export default {
  name: "HomePageChart",
  props: {//用于组件通信
    workId: {
      type: Number,
      require: true
    }
  },
  data() {
    return {
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
          data: ['积极情感', '消极情感', '中性情感']
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
              fontWeight: 600,
              fontSize: 12,
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
              fontSize: 12,
              fontFamily: 'Times New Roman'
            }
          }
        ],
        series: [
          {
            name: '积极情感',
            type: 'line',
            emphasis: {
              focus: 'series'
            },
            data: [30, 40, 35, 30, 50, 20, 15]
          },
          {
            name: '消极情感',
            type: 'line',
            emphasis: {
              focus: 'series'
            },
            data: [20, 15, 25, 14, 34, 25, 10]
          },
          {
            name: '中性情感',
            type: 'line',
            emphasis: {
              focus: 'series'
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
      selectCountry: '美国',
      selectMonth: '2023-07',
      countryEmo2: {},
      queryPolarityParams: {
        workId: this.workId,
        country: "美国",
        startTime: "2023-07",
        dayInterval: 7
      },
    }
  },
  async mounted() {
    await this.getAllCountries()
    this.getData()
    this.initLineChart()
    console.log("line chart")
  },
  methods: {
    getAllCountries() {
      getCountries({workId: this.workId}).then((res)=>{
        // alert("hello");
        this.countryOptions = res.data.map((item)=>{
          return {label: item, value: item}
        })
        this.countryOptions.push({label: "全球", value: "全球"})
        this.countryOptions.reverse()
      })
    },
    initLineChart() {
      this.myChart = this.$echarts.init(document.getElementById('myChart'))
      this.myChart.setOption(this.option)
    },
    getData() {//获取数据
      //调用接口
      this.queryPolarityParams.startTime = this.selectMonth+"-01";
      if (this.selectCountry === "全球") {
        this.queryPolarityParams.country = ""
      } else {
        this.queryPolarityParams.country = this.selectCountry
      }
      polarityCountDayInterval(this.queryPolarityParams).then((res) => {
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
      })
    },
    updateChart() {
      this.myChart.setOption(this.option);
    }
  }
}
</script>

<style lang="css" scoped>
</style>
