<template>
  <div id="container2" :style="{height: height2}"></div>
</template>

<script>
import {workScoreByWorkId} from "../../../api/WorkScoreAPI";

export default {
  props:{
    workId:{
      Type:Number,
      default:1
    },
    height: {
      type: String,
      require: false,
      default: '300px'
    },
    fontColor:{
      Type:String,
      default:"#e88226"
    }
  },
  data(){
    return {
      divWidth: 566,
      chart:null,
      data1: [
        {
          "id": null,
          "workId": null,
          "score": 4,
          "platform": "豆瓣",
          "createTime": null
        },
        {
          "id": null,
          "workId": null,
          "score": 3.5,
          "platform": "烂番茄",
          "createTime": null
        },
        {
          "id": null,
          "workId": null,
          "score": 3,
          "platform": "IMDb",
          "createTime": null
        },
        {
          "id": null,
          "workId": null,
          "score": 3.5,
          "platform": "平均",
          "createTime": null
        }
      ],
      name:'Score',
      option: null
    }
  },
  computed: {
    height2() {
      return this.height !== '300px'? this.height: "95%"
    }
  },
  mounted(){
    // console.log(this.workId)
    this.divWidth = document.getElementById("container2").clientWidth
    // console.log(this.divWidth)
    this.option = {
      title:{
        text:"作品评分图",
        show:false,
        left: 'center',
        textStyle: {
          fontSize: this.divWidth * 0.033,
          color: this.fontColor,
          textBorderWidth: 1.5,
          textBorderColor: '#000'
        }
      },
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
      xAxis: [
        {
          type: 'category',
          data: ['豆瓣', 'Amazon', 'Imdb', 'GoodReads','烂番茄'],
          axisTick: {
            alignWithLabel: true
          },
          axisLabel:{
            fontSize: this.divWidth * 0.025,
            color: '#ef8407',
            fontWeight: 'bolder',
            textBorderWidth: 1.5,
            textBorderColor: '#000'
          }
        }
      ],
      // backgroundColor: 'rgba(32,90,165,0.4)',
      yAxis: [
        {
          type: 'value',
          min: 0,
          max: 5,
          name: '评分',
          nameTextStyle: {
            fontWeight: 600,
            fontSize: this.divWidth * 0.0248,
            fontFamily: 'Times New Roman',
          },
          axisLabel:{
            fontSize:this.divWidth * 0.0247
          }
        }
      ],
      series: [
        {
          name: 'Score',
          type: 'bar',
          barWidth: '45%',
          barMaxWidth: 50, // 柱子的最大宽度
          data: [4.5, 3.2, 4.5, 3.9,4.9],
          // 设置柱条颜色
          colorBy: 'series',
          itemStyle: {
            color: 'rgba(158,43,45, 0.8)'
          }
        }
      ],
      textStyle:{
        fontSize: this.divWidth * 0.0351,
        color:this.fontColor,
      }
    }
    //调用接口，取得返回值，将返回值中的data直接复制给data1
    workScoreByWorkId({workId: this.workId}).then((res)=>{
      // console.log("1111")
      if (res.code === "0") {
        // console.log("2222")
        this.data1 = res.data
        const score=[]
        const platform=[]
        if (this.data1) {
          this.data1.forEach(item=>{
            score.push(item.score)
            platform.push(item.platform)
          })
        }
        this.option.xAxis.forEach(item=>{
          item.data=platform
        })
        this.option.series.forEach(item=>{
          item.data=score
        })
        // console.log(score)
        // console.log(platform)
        this.chart = this.$echarts.init(document.getElementById('container2'))
        // console.log(myChart)
        this.chart.setOption(this.option)
      }
    })
    this.handleResize = ()=> {
      this.divWidth = document.getElementById("container2").clientWidth
      this.chart.resize();
    }
    window.addEventListener('resize',  this.handleResize)
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
  },
}
</script>

<style scoped>
#container2{
  /*margin: 30px auto auto auto;*/
  margin-top:2%;
  height:85%;
}
</style>
