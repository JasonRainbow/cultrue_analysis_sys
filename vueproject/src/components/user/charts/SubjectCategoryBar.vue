<template>
  <div style="text-align: center">
    <h2 style="font-weight: bold">主题类别统计</h2>
    <div id="subjectCategoryChart" style="width:100%;height:350px"></div>
  </div>
</template>

<script>
export default {
  name: "SubjectCategoryBar",
  data() {
    return {
      subjectCategoryChart: null,
      option: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '3%',
          bottom: '1%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['翻译质量', '影视特效', '演员演技', '价值观念', '故事情节', '人物塑造'],
          axisTick: {
            alignWithLabel: true
          },
          axisLabel:{
            color: '#ef8407',
            fontWeight: 'bolder',
            textBorderWidth: 1.5,
            textBorderColor: '#000'
          }
        },
        yAxis: {
          type: 'value',
          name: '数量'
        },
        series: [
          {
            data: [20, 60, 50, 30, 20, 15],
            type: 'bar',
            barWidth: '45%',
            barMaxWidth: 50, // 柱子的最大宽度
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            },
            // 设置柱条颜色
            colorBy: 'series',
            itemStyle: {
              color: 'rgba(158,43,45, 0.8)'
            }
          }
        ]
      }
    }
  },
  mounted() {
    this.initSubjectCategoryChart()
  },
  methods: {
    initSubjectCategoryChart() {
      this.subjectCategoryChart = this.$echarts.init(document.getElementById('subjectCategoryChart'));
      console.log(this.subjectCategoryChart)
      this.subjectCategoryChart.setOption(this.option);
    }
  }
}
</script>

<style scoped>

</style>
