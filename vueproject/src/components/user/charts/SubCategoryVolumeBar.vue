<template>
  <div id="volumeBar">
    <div id="barChart">

    </div>
  </div>
</template>

<script>

import {getAllCommentNumBySubCategory} from "../../../api/commentAPI";

export default {
  name: "SubCategoryVolumeBar",
  data() {
    return {
      colorOption: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
        { offset: 0, color: '#14c8d4' },
        { offset: 1, color: '#43eec6' }
      ]),
      barWidth: '300px',
      barHeight: '400px',
      myChart: null,
      categoryData: []
    }
  },
  methods: {
    makeOption(type, symbol) {
      return {
        title: {
          text: '作品类型评论量'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          containLabel: true,
          left: 0,
          width: '85%'
        },
        yAxis: {
          type: 'category',
          data: this.categoryData.map((item)=>{
            return item.first
          }),
          inverse: true,
          axisLine: { show: false },
          axisTick: { show: false },
          axisLabel: {
            rotate: 15,
            color: '#dc5a15',
            fontSize: 15,
            fontWeight: 'bold',
          },
          axisPointer: {
            label: {
              show: true,
              margin: 30
            }
          }
        },
        xAxis: {
          type: 'value',
          axisLabel: {
            color: '#11977c',
            fontSize: 15,
            fontWeight: 'bold'
          }
        },
        animationDurationUpdate: 500,
        series: [
          {
            id: 'bar1',
            type: type,
            label: {
              show: true,
              position: 'right',
              valueAnimation: true,
              color: '#11977c',
              fontSize: 15,
              fontWeight: 'bold',
              formatter: '{c} 条'
            },
            symbolRepeat: true,
            symbolSize: ['90%', '70%'],
            barMaxWidth: 20, // 柱子的最大宽度
            barCategoryGap: '30%',
            universalTransition: {
              enabled: true,
              delay: function (idx, total) {
                return (idx / total) * 100;
              }
            },
            itemStyle: {
              borderRadius: 30,
              color: this.colorOption
            },
            data: this.categoryData.map((item)=>{
              return {
                value: item.second,
                symbol: symbol
              }
            }),
          },

        ]
      };
    },
    updateBarSize() {
      if (this.myChart) {
        this.myChart.resize()
      }
    },
    getData() {
      getAllCommentNumBySubCategory().then((res)=>{
        if (res.code === "0") {
          this.categoryData = res.data
          this.initChart()
        }
      })
    },
    initChart() {
      this.myChart = this.$echarts.init(document.getElementById("barChart"))

      const options = [
        this.makeOption('bar'),
        this.makeOption('pictorialBar', 'diamond')
      ];
      let optionIndex = 0;
      let option = options[optionIndex];
      this.myChart.setOption(options[optionIndex++])
      setInterval( ()=>{
        optionIndex = (optionIndex + 1) % options.length;
        this.myChart.setOption(options[optionIndex]);
      }, 5000);
      this.handleResize = ()=>{
        this.updateBarSize()
      }
      window.addEventListener("resize", this.handleResize)
    }
  },
  created() {
    this.getData()
  },
  mounted() {

  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
}
</script>

<style scoped>

#barChart {
  top: 30px;
  height: 93%;
  width: 95%;
  z-index: 2;
}

#volumeBar {
  display: flex;
  width: 100%;
}

</style>
