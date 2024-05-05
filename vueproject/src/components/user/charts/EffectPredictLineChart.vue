<template>
  <div style="text-align: center">
    <h2 style="font-weight: bold">传播效果预测</h2>
    <div style="margin-top: 15px; width: 100%;" >
      <el-select v-model="selectMetric" class="custom-select" placeholder="请选择预测指标" @change="changeMetric">
        <el-option
          v-for="item in metricOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-date-picker
        class="custom-select2"
        v-model="selectYear"
        type="year"
        :clearable="true"
        value-format="yyyy"
        @change="this.getData"
        :picker-options="pickerOptions"
        placeholder="选择年">
      </el-date-picker>
    </div>
    <div class="chart-centerAlign">
      <div id="effectPredictChart" style="width:800px;height:500px;margin-top: 15px"></div>
    </div>

  </div>
</template>

<script>
import {getEffectPredictionByWorkIdAndYear} from "../../../api/effectPredictAPI";

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
      selectMetric: "传播效果得分",
      metricOptions: [
        {
          value: '传播效果得分',
          label: '传播效果得分',
        },
        {
          value: '积极情感占比',
          label: '积极情感占比',
        },
        {
          value: '消极情感占比',
          label: '消极情感占比',
        },
        {
          value: '中立情感占比',
          label: '中立情感占比',
        },
      ],
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
              // interval: 2,
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
            name: '指标值',
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
            data: [90.4, 88.1, 87.5, 78.9, 82.5, 84.3, 86.7, 86.5, 85.2, 83.3, 85.6, 86]
          },
          {
            name: '预测值',
            type: 'line',
            emphasis: {
              focus: 'series'
            },
            data: [89, 88.4, 86.2, 83.1, 80.3, 85.3, 87.5, 85.2, 84.9, 83, 84, 87],
            lineStyle: {
              type: "dashed"
            }
          }
        ]
      },
      prediction_data: {

      }
    }
  },
  created() {
    this.getData()
  },
  mounted() {
    // this.initChart()
  },
  methods: {
    updateInfo(key1, key2) {
      this.option.series[0].data = this.prediction_data.map((item)=>{
        return item[key1] === null?null:Math.round(item[key1]*10000) / 100
      })
      this.option.series[1].data = this.prediction_data.map((item)=>{
        return item[key2] === null?null:Math.round(item[key2]*10000) / 100
      })
      this.option.xAxis[0].data = this.prediction_data.map((item)=>{
        return item["date"]
      })
      this.initChart()
    },
    changeMetric() {
      switch (this.selectMetric) {
        case "传播效果得分": {
          this.updateInfo("trueScore", "predScore")
          break
        }
        case "积极情感占比": {
          this.updateInfo("truePosProportion", "predPosProportion")
          break
        }
        case "消极情感占比": {
          this.updateInfo("trueNegProportion", "predNegProportion")
          break
        }
        case "中立情感占比": {
          this.updateInfo("trueNeuProportion", "predNeuProportion")
          break
        }
      }
    },
    getData() {
      getEffectPredictionByWorkIdAndYear({workId: this.workId, year: this.selectYear}).then((res)=>{
        if (res.code === "0") {
          this.prediction_data = res.data
          this.changeMetric()
        }
      })
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
.chart-centerAlign {
  display: flex;
  justify-content: center;
}
</style>
