<template>
  <div>
    <span style="margin-right: 8px" class="font-bold">主题切换：</span>
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
            radius: ['40%', '60%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
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
              const myChart = this.$echarts.init(document.getElementById('container1'))
              // console.log("@@@",myChart)
              myChart.setOption(this.option)
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
  height:290px;
}
</style>
