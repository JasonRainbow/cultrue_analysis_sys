<template>
  <div style="text-align: center">
    <h2 style="font-weight: bold; margin-bottom: 10px">主题类别统计</h2>
    <div id="subjectCategoryChart" style="width:100%;height:500px"></div>
  </div>
</template>

<script>
import {getAllByWorkId} from "../../../api/SubjectAnalysisAPI";

export default {
  name: "SubjectCategoryBar",
  props: {
    workId: {
      required: true,
      type: Number
    }
  },
  data() {
    return {
      subjectCategoryChart: null,
      option: {
        color: ['#31fdd6', '#EA0C31', '#FBFF00', '#FF917C'],
        legend: {
          data: ['积极', '消极', "中立"]
        },
        radar: {
          // shape: 'circle',
          indicator: [
            { name: '翻译质量', max: 6500 },
            { name: '影视特效', max: 16000 },
            { name: '演员演技', max: 30000 },
            { name: '价值观念', max: 40000 },
            { name: '故事情节', max: 50000 },
            { name: '人物塑造', max: 25000 },
          ],
          center: ["50%", "53%"],
          axisName: {
            color: '#ef8407',
            fontSize: 16,
            fontWeight: "bolder",
            borderRadius: 3,
            padding: [3, 5]
          },
          splitArea: {
            areaStyle: {
              color: ['#77EADF', '#26C3BE', '#64AFE9', '#428BD4'],
              shadowColor: 'rgba(0, 0, 0, 0.2)',
              shadowBlur: 10
            }
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(211, 253, 250, 0.8)'
            }
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(211, 253, 250, 0.8)'
            }
          },
          startAngle: 90,
          splitNumber: 4,
        },
        series: [
          {
            name: 'Budget vs spending',
            type: 'radar',
            symbol: "roundRect",
            emphasis: {
              lineStyle: {
                width: 5
              },
              focus: "self",
              label: {
                show: true
              }
            },
            data: [
              {
                value: [4200, 3000, 20000, 35000, 50000, 18000],
                name: '积极',
                label: {
                  // show: true,
                  formatter: function (params) {
                    return params.value;
                  }
                }
              },
              {
                value: [5000, 14000, 28000, 26000, 42000, 21000],
                name: '消极',
                label: {
                  // show: true,
                  formatter: function (params) {
                    return params.value;
                  }
                }
              },
              {
                value: [2000, 5000, 28000, 26000, 42000, 21000],
                name: '中立',
                label: {
                  // show: true,
                  formatter: function (params) {
                    return params.value;
                  }
                }
              }
            ]
          }
        ]
      }
    }
  },
  created() {
    getAllByWorkId({workId: this.workId}).then(res=>{
      if (res.code === "0") {
        this.option.radar.indicator = res.data.map(item => {
          return {name: item.subject, value: this.max(item.positive, item.neutrality, item.negative)}
        })
        this.option.series[0].data[0].value = res.data.map(item=>{
          return item.positive
        })
        this.option.series[0].data[1].value = res.data.map(item=>{
          return item.negative
        })
        this.option.series[0].data[2].value = res.data.map(item=>{
          return item.neutrality
        })
        this.subjectCategoryChart.setOption(this.option);
      }
    })
  },
  mounted() {
    this.initSubjectCategoryChart()
  },
  methods: {
    initSubjectCategoryChart() {
      this.subjectCategoryChart = this.$echarts.init(document.getElementById('subjectCategoryChart'));
      // console.log(this.subjectCategoryChart)
      this.subjectCategoryChart.setOption(this.option);
    },
    max(x, y ,z) {
      let tmp = x > y? x: y
      return tmp > z? tmp: z
    }
  }
}
</script>

<style scoped>

</style>
