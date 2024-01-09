<template>
  <div style="text-align: center;height:100%;margin-top: 30px">
<!--    <div :style="{'margin-bottom': '5px', marginLeft: '15px', 'font-size': divWidth * 0.0285 + 'px', fontSize: '19px', textAlign: 'left'}">{{value}} 情感分布</div>-->
<!--    <span :style="{'margin-right': '4px', 'font-size': divWidth * 0.02 + 'px'}" class="">月份选择：</span>-->
<!--    <el-select class="custom-select3" v-model="value" :size="inputSize" placeholder="请选择" @change="SelectChanged" style="">-->
<!--      <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.value"-->
<!--        :label="item.label"-->
<!--        :value="item.value">-->
<!--      </el-option>-->
<!--    </el-select>-->
    <div class="block">
      <span :style="{'margin-right': '4px', 'font-size': divWidth * 0.02 + 'px'}" class="">月份选择：</span>
      <el-date-picker
        v-model="time"
        type="month"
        placeholder="选择月"
        @change="SelectChanged">
      </el-date-picker>
    </div>
    <div id="container1"></div>
  </div>
</template>

<script>
import {getSubjectsByWorkId, subjectAnalysisByWorkId} from "../../../api/SubjectAnalysisAPI";
import {getMonthAnalysisResult} from "../../../api/polarityAPI";
export default {
  name:'Subject',
  props:{
    workId:{
      Type:Number,
    },
    country:{
      Type:String,
    }
  },
  data(){
    return {
      divWidth: 566,
      inputSize: 'mini',
      chart:null,
      time:'2023-02',
      params:{
        country:this.country,
        selectMonth:this.time
      },
      options: [
        {
        value: '积极情感',
        label: '积极情感'
        },
        {
          value: '中性情感',
          label: '中性情感'
        },
        {
          value: '消极情感',
          label: '消极情感'
        }],
      option: null,
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
    // console.log(this.workId,this.country)
    this.divWidth = document.getElementById("container1").clientWidth
    if (this.divWidth < 600) {
      this.inputSize = "mini"
    } else if (this.divWidth < 760) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }
    this.option = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '4%',
        left: 'center',
        textStyle: {
          color: '#000',
          fontSize:25
        },
      },
      series: [
        {
          // name: this.value,
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
                color: "#000",
                fontSize : this.divWidth * 0.024
              }
            }
          },
          emphasis: {
            label: {
              show: true,
              fontSize: this.divWidth * 0.0265,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: true,
            lineStyle:{
              width:2
            }
          },
          data: [
            { value: 1048, name: '积极' },
            { value: 735, name: '消极' },
            { value: 580, name: '中立' }
          ]
        }
      ]
    };
    this.createGraph()
    window.addEventListener('resize',  ()=> {
      this.chart.resize();
      this.divWidth = document.getElementById("container1").clientWidth
    })
  },
  methods:{
    createGraph(){
        //value值为主题，workId为作品id，将请求返回的data直接赋值给data1，也可以将data1删掉(data1是调试的时候用的)，直接将请求返回的data赋值给下面循环中的item.data
        // subjectAnalysisByWorkId({workId: this.workId
        //   , subject: this.value}).then((res)=>{
        //     if (res.code === "0") {
        //       this.data1 = res.data
        //       this.option.series.forEach(item=>{
        //         item.data=this.data1
        //       })
        //       this.chart = this.$echarts.init(document.getElementById('container1'))
        //       // console.log("@@@",myChart)
        //       this.chart.setOption(this.option)
        //     }
        // }),
      if(this.workId!==0 && this.workId !=='0'){
        this.params.workId=this.workId
      }
      // console.log(this.params,'params')
      this.params.selectMonth=this.toDate(this.time)
      getMonthAnalysisResult(this.params).then((res)=>{
        // console.log(res,'res')
        if(res.code==='0'){
          // console.log(res.data,'result')
          this.option.series.forEach(item=>{
            // console.log(item,'item')
            item.data.forEach(data=>{
              if(res.data===null){
                data.value=0
              }else{
                if(data.name==='积极'){
                  // console.log(true)
                  data.value=res.data.positive
                }else if(data.name==='消极'){
                  data.value=res.data.negative
                }else if(data.name==='中立'){
                  data.value=res.data.neutrality
                }
              }
            })
            // item.data=this.data1
          })
          this.chart = this.$echarts.init(document.getElementById('container1'))
          // console.log("@@@",this.chart)
          // console.log(this.option.series,'series')
          this.chart.setOption(this.option)
        }
      })

    },
    SelectChanged(){
      console.log(this.toDate(this.month),"month")
      this.createGraph()
    },
    toDate(time){
      let datetime = new Date(time);
      let year = datetime.getFullYear();
      let month = datetime.getMonth();
      let date = datetime.getDate();
      let result = year +
        '-' +
        ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1))
        //   + '-' +
        // ((date + 1) < 10 ? '0' + date : date);
      return result
    }
  },
  created() {

  }
}
</script>

<style scoped>
@import "../../../assets/styles/mystyle.css";

#container1{
  margin-top: 3%;
  height:500px;
}
</style>
