<template>
  <div style="text-align:center;margin: 30px auto; height: 100%; width: 90%">
    <h2>{{year===0?'全部年份':('近'+year+'年')}} 评论数量变化趋势</h2>
    <div style="margin-top: 15px; width: 100%;" >
      <el-select v-model="year" placeholder="请选择" @change="selectChanged" >
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div id="myChart1" :style="{margin: '10px auto', width: width, height: height2}"></div>
  </div>
</template>

<script>
import {getCommentNumByWorkIdAndYear} from "../../../api/commentAPI";
export default {
  name: "NearCommentNumLineChart",
  props: {//用于组件通信
    workId: {
      type: Number,
      require: true
    },
    country:{
      type:String,
      require:true
    },
    width: {
      type: String,
      require: false,
      default: "1000px"
    },
    height: {
      type: String,
      require: false,
      default: "500px"
    },
  },
  computed:{
    height2(){
      return this.height !== "400px" ? this.height : "400px"
    }
  },
  data() {
    return {
      year:1,
      options: [{
        value: 1,
        label: '近一年'
      }, {
        value: 2,
        label: '近两年'
      },{
        value: 3,
        label: '近三年'
      },
        {
          value: 4,
          label: '近四年'
        },
        {
          value: 5,
          label: '近五年'
        }],
      option: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['评论数量'],
          textStyle: {
            color: "#e2652f",
            fontSize: 15
          }
        },
        grid: {
          left: '3%',
          right: '8%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            name: '日期',
            boundaryGap: false,
            nameTextStyle: {
              fontWeight: "bold",
              fontSize: 14,
              fontFamily: 'Times New Roman'
            },
            data: ['2023-01', '2023-02', '2023-03', '2023-04', '2023-05', '2023-06', '2023-07']
          }
        ],
        yAxis: [
          {
            type: 'value',
            min: 0,
            // max: 100,
            name: '数量',
            nameTextStyle: {
              fontWeight: 600,
              fontSize: 15,
              fontFamily: 'Times New Roman'
            },
          }
        ],
        series: [
          {
            name: '评论数量',
            type: 'line',
            smooth:true,
            emphasis: {
              focus: 'series'
            },
            data: [50, 45, 40, 56, 16, 55, 75]
          }
        ]
      },

      myChart: {},
      params: {
        country:this.country,
        year:this.year
      },
    }
  },
  mounted() {
    this.initLineChart()
    this.getData()
    this.handleResize = ()=> {
      this.myChart.resize();
    }
    this.handleLoad = ()=>{
      this.myChart.resize()
    }
    window.addEventListener('resize',  this.handleResize)
    window.onload = this.handleLoad
  },
  beforeDestroy() {
    if (this.handleResize) {
      window.removeEventListener("resize", this.handleResize)
    }
    if (this.handleLoad) {
      window.removeEventListener("load", this.handleLoad)
    }
  },
  methods: {
    selectChanged(){
      this.getData()
    },
    initLineChart() {
      this.myChart = this.$echarts.init(document.getElementById('myChart1'))
      this.myChart.setOption(this.option,"option")
    },
    getData() {//获取数据
      //调用接口
      if(this.workId !== 0){
        this.params.workId = this.workId
      }
      this.params.country=this.country
      this.params.year=this.year
      getCommentNumByWorkIdAndYear(this.params).then((res)=>{
        if(res.code==='0'){
          let months=[]
          let counts=[]
          for(let i=0;i<res.data.length;i++){
            months.push(res.data[i].time)
            counts.push(res.data[i].count)
          }
          this.option.xAxis[0].data=months
          this.option.series[0].data=counts
          this.updateChart();
        }
      })

    },
    updateChart() {
      this.myChart.setOption(this.option);
    }
  }
}
</script>

<style scoped lang="scss">

</style>
