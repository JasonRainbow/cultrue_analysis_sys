<template>
  <div id="platFormChart" style="height: 100%;width: 100%"></div>
</template>

<script>
import {countMonitorWorkPlatform} from "../../../api/monitor_workAPI";

export default {
  name: "DataSource",
  props:{//用于组件通信
    workId: {
      type: Number,
      require: true
    }
  },
  data(){
    return{
      option: {
        legend: {
          top: 'bottom'
        },
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '数据来源占比图',
            type: 'pie',
            radius: [50, 250],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: 40, name: 'Youtube' },
              { value: 38, name: 'Twitter' },
              { value: 32, name: '烂番茄' },
              { value: 30, name: 'Amazon' },
              { value: 28, name: 'Facebook' },
              { value: 26, name: '豆瓣' },
            ]
          }
        ]
      },
      //请求参数
      queryPlatFormParams: {
        workId: this.workId
      },
      platFormChart: {},
    }
  },
  methods: {
    initPlatFormChart(){//初始化图表
      this.platFormChart = this.$echarts.init(document.getElementById('platFormChart'))
      //this.getPlatFormData()
      this.platFormChart.setOption(this.option)
    },
    getPlatFormData(){//异步请求 获取平台数据
      countMonitorWorkPlatform(this.queryPlatFormParams).then((res)=>{
        if (res.code === "0") {
          this.option.series[0].data = res.data.map((item)=>{
            return {value: item.count, name: item.platform}
          });
          console.log(this.option.series[0].data)
          this.updatePlatFormChart()
        }
      })
    },
    updatePlatFormChart(){
      this.platFormChart.setOption(this.option)
    }
  },
  mounted(){
    console.log("Data")
    this.initPlatFormChart()
  },
  create() {
    console.log("1111")
  }
}
</script>

<style scoped>

</style>
