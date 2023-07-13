<template>
  <div style="">
    <div style="margin-bottom: 20px">数据来源占比</div>
    <div id="platFormChart" style="height: 450px;width: 100%"></div>
  </div>
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
          top: '5%',
          left: 'center',
          textStyle: {
            color: '#fff'
          },
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
            radius: ['40%', '70%'],
            center: ['52%', '60%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 10,
              borderColor: '#ece7e7',
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
          this.initPlatFormChart()
        }
      })
    },
    updatePlatFormChart(){
      // this.platFormChart.setOption(this.option)
      let _this = this
      window.addEventListener('resize',function(){
        _this.platFormChart.resize();
      })
    }
  },
  mounted(){
    // console.log("Data")
    // this.getPlatFormData()
    this.initPlatFormChart()
  },
  create() {
    // console.log("1111")
  }
}
</script>

<style scoped>

</style>
