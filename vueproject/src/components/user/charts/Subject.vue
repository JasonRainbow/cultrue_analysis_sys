<template>
  <div style="text-align: center;height:100%">
    <h4 style="margin-bottom: 5px">{{value}} 情感分布</h4>
    <span style="margin-right: 4px" class="">指标切换：</span>
    <el-select v-model="value" size="small" placeholder="请选择" @change="SelectChanged" style="">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <div id="container1"></div>
  </div>
</template>

<script>
import {subjectAnalysisByWorkId} from "../../../api/SubjectAnalysisAPI";

export default {
  name:'Subject',
  props:{
    workId:{
      Type:Number,
      default:1
    }
  },
  data(){
    return {
      chart:null,
      options: [{
        value: '故事情节',
        label: '故事情节'
      },
        {
          value: '翻译质量',
          label: '翻译质量'
        },
        {
          value: '影视特效',
          label: '影视特效'
        }],
      value:'故事情节',
      option:{
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center',
          textStyle: {
            color: '#fff'
          },
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['45%', '65%'],
            center: ['50%', '60%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            // label: {
            //   show: false,
            //   position: 'center'
            // },
            label : {
              normal : {
                formatter: '{b}:{c}({d}%)',
                textStyle : {
                  color: "#ffffff",
                  fontSize : 15
                }
              }
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true,
              lineStyle:{
                width:1
              }
            },
            data: [
              { value: 1048, name: '积极' },
              { value: 735, name: '消极' },
              { value: 580, name: '中立' }
            ]
          }
        ]
      },
      data1:[
        {
          name: "积极",
          value: 225
        },
        {
          name: "消极",
          value: 89
        },
        {
          name: "中立",
          value: 155
        }
      ]
    }
  },
  mounted(){
      // console.log("@@@@@@","mounted")
      this.createGraph()
    window.addEventListener('resize',  ()=> {
      this.chart.resize();
    })
  },
  methods:{
    createGraph(){
        //value值为主题，workId为作品id，将请求返回的data直接赋值给data1，也可以将data1删掉(data1是调试的时候用的)，直接将请求返回的data赋值给下面循环中的item.data
        subjectAnalysisByWorkId({workId: this.workId
          , subject: this.value}).then((res)=>{
            if (res.code === "0") {
              this.data1 = res.data
              this.option.series.forEach(item=>{
                item.data=this.data1
              })
              this.chart = this.$echarts.init(document.getElementById('container1'))
              // console.log("@@@",myChart)
              this.chart.setOption(this.option)
            }
        })

    },
    SelectChanged(){
        this.createGraph()
    },
  }
}
</script>

<style scoped>
#container1{
  margin-top: 3%;
  height:70%;
}
</style>
