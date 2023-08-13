<template>
  <div id="container2"></div>
</template>

<script>
import {workScoreByWorkId} from "../../../api/WorkScoreAPI";

export default {
  props:{
    workId:{
      Type:Number,
      default:1
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
  mounted(){
    this.divWidth = document.getElementById("container2").clientWidth
    // console.log(this.divWidth)
    this.option = {
      title:{
        text:"作品评分图",
        left: 'center',
        textStyle: {
          fontSize: this.divWidth * 0.033,
          color: '#ccc'
        }
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      grid: {
        left: '5%',
        right: '5%',
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
            fontSize: this.divWidth * 0.0247
          }
        }
      ],
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
          barWidth: '60%',
          data: [4.5, 3.2, 4.5, 3.9,4.9]
        }
      ],
      textStyle:{
        fontSize: this.divWidth * 0.0351,
        color:'#fff',
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
    window.addEventListener('resize',  ()=> {
      this.chart.resize();
    })
  }
}
</script>

<style scoped>
#container2{
  /*margin: 30px auto auto auto;*/
  margin-top:5%;
  height:85%;
}
</style>
