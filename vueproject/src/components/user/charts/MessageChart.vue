<template>
  <div id="msgChart" style="text-align:center;height: 100%;width: 100%;background-color: #2c3e50; border-radius: 10px"></div>
</template>
<script>
import {polarityCountDayInterval} from "../../../api/polarityAPI";
export default{
  name: "MessageChart",
  props:{//用于组件通信
    workId: {
      type: Number,
      require: true
    }
  },
  data() {
    return {
      posData:[],
      negData:[],
      divWidth: 475,
      option: null,
      queryMsgParam: {
        workId: this.workId,
        startTime: '2023-06-28',
        dayInterval:7
      },
      msgChart:{}
    }
  },
  mounted() {
    this.divWidth = document.getElementById("msgChart").clientWidth
    this.option = {
      title: {
        text: "国际总体信息量变化趋势",
        left: 'center',
        textStyle: {
          fontSize: this.divWidth * 0.055,
          color:'#6495ed',
          fontWeight:'bolder',
        },
      },
      legend: {
        bottom: '-4px',
        textStyle: {
          color: "darkturquoise",
          fontSize: this.divWidth * 0.029
        }
      },
      tooltip: {
        trigger: 'axis',
        backgroundColor: 'rgba(19, 25, 47, 0.6)',
        borderColor: 'deepskyblue',
        textStyle: {
          color: 'white', //设置文字颜色
          fontSize: this.divWidth * 0.02,
        }
      },
      grid: {
        top: '20%',
        left: '1%',
        right: '2%',
        bottom: '3%',
        containLabel: true,
      },
      xAxis: [
        {
          type: 'category',
          name: '日期',
          boundaryGap: false,
          backgroundColor: '#7581BD',
          splitLine: {
            show: false
          },
          axisLabel: {
            interval:0 ,
            fontWeight: 400,
            fontSize: this.divWidth * 0.024,
            fontFamily: 'Times New Roman',
            formatter : function(params){
              let newParamsName = "";
              let paramsNameNumber = params.length;
              let provideNumber = 4;
              let rowNumber = Math.ceil(paramsNameNumber / provideNumber);
              if (paramsNameNumber > provideNumber) {
                for (let p = 0; p < rowNumber; p++) {
                  let tempStr = "";
                  let start = p * provideNumber;
                  let end = start + provideNumber;
                  if (p == rowNumber - 1) {
                    tempStr = params.substring(start, paramsNameNumber);
                  } else {
                    tempStr = params.substring(start, end) + "\n";
                  }
                  newParamsName += tempStr;
                }

              } else {
                newParamsName = params;
              }
              return newParamsName
            }
          },
          data: ['2023-06-29', '2023-06-29', '2023-06-30', '2023-07-01', '2023-07-02', '2023-07-03', '2023-07-04']
        }
      ],
      yAxis: [
        {
          type: 'value',
          min: 0,
          max: 350,
          name: '信息量',
          axisLabel: {
            fontWeight: 600,
            fontSize: this.divWidth * 0.028,
            fontFamily: 'Times New Roman'
          }
        }
      ],
      series: [
        {
          name: '积极情感',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: this.divWidth * 0.016,
          stack: "a",
          sampling: 'average',
          itemStyle: {
            color: '#0770FF'
          },
          areaStyle: {
            color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgba(58,77,233,0.8)'
              },
              {
                offset: 1,
                color: 'rgba(58,77,233,0.3)'
              }
            ])
          },
          data: this.posData
        },
        {
          name: '消极情感',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: this.divWidth * 0.016,
          sampling: 'average',
          itemStyle: {
            color: '#F2597F'
          },
          areaStyle: {
            color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgba(213,72,120,0.8)'
              },
              {
                offset: 1,
                color: 'rgba(213,72,120,0.3)'
              }
            ])
          },
          data: this.negData
        }
      ]
    };
    this.getMsgData()
    this.initMsgChart()
    this.getTootipTimer()
    window.addEventListener('resize',  ()=> {
      this.divWidth = document.getElementById("msgChart").clientWidth
      // console.log(this.divWidth)
      this.msgChart.resize();
      // console.log("resize")
    })
  },
  methods: {
    //初始化图表
    initMsgChart() {
      this.msgChart = this.$echarts.init(document.getElementById('msgChart'))
      this.msgChart.setOption(this.option)
      // 鼠标移入停止定时器
      this.msgChart.on("mouseover", () => {
        clearInterval(this.tootipTimer);
      })
      // 鼠标移出启动定时器
      this.msgChart.on("mouseout", () => {
        this.getTootipTimer();
      })
    },
    //获取数据
    getMsgData(){
      polarityCountDayInterval(this.queryMsgParam).then((res) => {
        if(res.code === "0"){
          this.option.series[0].data = res.data.statisticsInfo.map((item) => {
            return item.positive
          })
          this.option.series[1].data = res.data.statisticsInfo.map((item) => {
            return item.negative
          })
          this.updateMsgChart()
        }
      })
    },
    //更新图表
    updateMsgChart() {
      this.msgChart.setOption(this.option);
    },
    // 定时器
    getTootipTimer(){
      let index = 0; // 播放所在下标
      this.tootipTimer = setInterval(() => {
        // echarts实现定时播放tooltip
        this.msgChart.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: index
        });
        index++;
        if(index > 7) {
          index = 0;
        }
      }, 1500)
    },
  }
}
</script>
<style scoped>

</style>
