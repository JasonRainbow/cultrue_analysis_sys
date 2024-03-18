<template>
  <div style="text-align: center">
    <h2 style="font-weight: bold">传播效果预测</h2>
    <div style="margin-top: 15px; width: 100%;" >
      <el-date-picker
        class="custom-select2"
        v-model="selectYear"
        type="year"
        :clearable="false"
        value-format="yyyy"
        @change="this.getData"
        :picker-options="pickerOptions"
        placeholder="选择年">
      </el-date-picker>
    </div>
    <div id="effectPredictChart" style="width:100%;height:350px;margin-top: 15px"></div>
  </div>
</template>

<script>
export default {
  name: "EffectPredictLineChart",
  props: {
    workId: {
      type: Number,
      require: true
    }
  },
  data() {
    return{
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        }
      },
      selectYear: '2023',
      myChart: {},
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
          data: ['真实值', '预测值'],
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
            name: '月份',
            boundaryGap: false,
            axisLabel: {
              interval: 0,
              fontWeight: "bold",
              fontSize: 14,
              fontFamily: 'Times New Roman',
              formatter : function(params){
                let newParamsName = "";
                let paramsNameNumber = params.length;
                let provideNumber = 4;
                let rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                if (paramsNameNumber > provideNumber) {
                  for (let p = 0; p < rowNumber; p++) {
                    let tempStr = "";
                    let start = p * provideNumber;
                    let end = start + provideNumber;
                    if (p == rowNumber - 1) {
                      tempStr = params.substring(start, paramsNameNumber);
                    } else {
                      tempStr = params.substring(start, end) + "\n";
                    }
                    newParamsName += tempStr;
                  }

                } else {
                  newParamsName = params;
                }
                return newParamsName
              },
            },
            data: ['1', '2', '3', '4', '5', '6', '7','8','9', '10', '11', '12']
          }
        ],
        yAxis: [
          {
            type: 'value',
            min: 0,
            max: 100,
            name: '传播效果得分',
            nameTextStyle: {
              fontWeight: 600,
              fontSize: 15,
              fontFamily: 'Times New Roman'
            }
          }
        ],
        series: [
          {
            name: '真实值',
            type: 'line',
            emphasis: {
              focus: 'series'
            },
            data: [30, 40, 35, 30, 50, 20, 15, 16, 30, 40, 35, 30]
          },
          {
            name: '预测值',
            type: 'line',
            emphasis: {
              focus: 'series'
            },
            data: [20, 15, 25, 14, 34, 25, 10, 7, 5, 25, 14, 34]
          }
        ]
      },
    }
  },
  created() {
    this.getData()
  },
  mounted() {
    this.initChart()
  },
  methods: {
    getData() {

    },
    initChart() {
      this.myChart = this.$echarts.init(document.getElementById('effectPredictChart'))
      this.myChart.setOption(this.option)
    }

  }
}
</script>

<style scoped>
@import "../../../assets/styles/mystyle.css";

</style>
