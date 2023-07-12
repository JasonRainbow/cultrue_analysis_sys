<!-- echarts模板，有局部刷新需要在mounted自行开启 -->
<template>      
    <div ref="refChart" :style="{ height: kHOne + 'px'}"></div>
</template>

<script>
require("../../../assets/theme/shine")
export default {
  name: '',
  components: {},
  data () {
  return {
    // 获取浏览器可视区域高度（包含滚动条）、 window.innerHeight
    // 获取浏览器可视区域高度（不包含工具栏高度）、document.documentElement.clientHeight
    // 获取body的实际高度  (三个都是相同，兼容性不同的浏览器而设置的) document.body.clientHeight
    screenHeight: window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
    screenWidth: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
    chartInstance: null,
    allData: [],
    allData2: [],
    // 浏览器高度
    kHOne: null,
    // 浏览器宽度
    kWOne: null,
    // 浏览器字体大小
    kFOne: null,
    // 自适应浏览器获取宽高大小定时器
    koiTime: null,
    // 局部刷新定时器    
    koiTimer: null, 
    // tootip定时器ID-用于清除定时器
    tootipTimer: null       
  }
  },
  created () {},
  beforeMount(){
    // Echarts页面加载进来时，首先设置高度进行显示图表进行初始化
    this.kHOne = Math.round(this.screenHeight * 0.3)
  },
  mounted(){
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenWidth, false);
    // 鼠标移动时触发
    //window.addEventListener('mousemove',this.getHeight, false);
    // 自适应浏览器获取宽高大小定时器
    this.resizeScreen();
    // 图表初始化
    this.initChart();
    // 获取接口数据
    this.getData();
    // 调用Echarts图表自适应方法
    this.screenAdapter();    
    // Echarts图表自适应
    window.addEventListener("resize", this.screenAdapter);
    // 局部刷新定时器
    //this.getDataTimer();
    // Tootip刷新定时器
    this.getTootipTimer();        
  },
  beforeDestroy () {
    // 清除tootip刷新定时器
    clearInterval(this.tootipTimer);
    this.tootipTimer = null;     
    // 销毁Echarts图表
    this.chartInstance.dispose();
    this.chartInstance = null;
  },
  destroyed() {
    // 清除自适应屏幕定时器
    clearInterval(this.koiTime);
    this.koiTime = null;
    // 清除局部刷新定时器
    clearInterval(this.koiTimer);
    this.koiTimer = null;
    // 页面大小改变时触发销毁
    window.removeEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发销毁
    window.removeEventListener('resize',this.getScreenWidth, false);
    // Echarts图表自适应销毁
    window.removeEventListener("resize", this.screenAdapter);
  },
  methods: {
    // 自适应浏览器获取宽高大小定时器
    resizeScreen(){
      this.koiTime = setInterval(() => {
        this.getScreenHeight();
        this.getScreenWidth();
      }, 200)
    },
    // 获取浏览器高度进行自适应
    getScreenHeight() {
        this.screenHeight = window.innerHeight || document.documentElement.innerHeight || document.body.clientHeight;
        // 四舍五入取整数
        this.kHOne = Math.round(this.screenHeight * 0.3);
        //console.log("高度->"+this.screenHeight +"-"+ this.kHOne);
    },
    // 字体大小根据宽度自适应
    getScreenWidth(){
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      // 浏览器字体计算
      this.kFOne = Math.round(this.screenWidth / 100);
      // 浏览器宽度宽度
      this.kWOne = this.screenWidth;       
      //console.log("宽度->"+this.screenWidth);
    },
    initChart() {
      this.chartInstance = this.$echarts.init(this.$refs.refChart,'shine');
      const initOption = {
        title: {
          text: "🍁雾霭与雾霾浓度",
          left: 'left'
        },           
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          top: '20%',
          left: '1%',
          right: '2%',
          bottom: '3%',
          containLabel: true,
        },
        color: ['#019688', '#119AC2'],
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '浓度数据',
            // 字体大小
            // nameTextStyle: {
            //    fontSize: 12
            // },
            axisTick: { // 轴刻度线
              show: false,
            },
            // 刻度文字颜色
            axisLabel: { color: '#808080' },
            // y轴刻度设置
            axisLine: {
              lineStyle: {
                width: 2,
                color: '#019688',
              },
            },
            // y轴分隔线设置
            splitLine: {
              lineStyle: {
                color: 'rgba(226,226,226,0.5)',
              },
            },
          },
          {
            type: 'value',
            name: '测量原始值',
            interval: 2,
            axisTick: { // 轴刻度线
              show: false,
            },
            axisLabel: { color: '#808080' },
            axisLine: {
              lineStyle: {
                width: 2,
                color: '#019688',
              },
            },
            // y轴分隔线设置
            splitLine: {
              lineStyle: {
                color: 'rgba(226,226,226,0.5)',
              },
            },
            // splitArea: {
            //   show: true,
            //   areaStyle: {
            //     color: ['rgba(250,250,250,0.3)', 'rgba(226,226,226,0.3)'],
            //   },
            // },
          },
        ],
        series: [
          {
            name: '浓度数据',
            type: 'line',
            smooth: true,
            symbolSize: 6,
            areaStyle: {},
            itemStyle: {
              borderWidth: 2,
            },
          },
          {
            name: '测量原始值',
            type: 'line',
            yAxisIndex: 1,
            smooth: true,
            symbolSize: 6,
            areaStyle: {},
            itemStyle: {
              borderWidth: 2,
            },
          },
        ],       
      };
      // 图表初始化配置
      this.chartInstance.setOption(initOption);
      // 鼠标移入停止定时器
      // this.chartInstance.on("mouseover", () => {
      //   clearInterval(this.koiTimer);
      // });

      // 鼠标移入启动定时器
      // this.chartInstance.on("mouseout", () => {
      //   this.getDataTimer();
      // });
      // 鼠标移入停止定时器
      this.chartInstance.on("mouseover", () => {
        clearInterval(this.tootipTimer);
      });

      // 鼠标移入启动定时器
      this.chartInstance.on("mouseout", () => {
        this.getTootipTimer();
      });              
    },
    getData() {
      // 调用接口方法
      // getModuleData().then(res => {
      //       this.allData = res.data;
      //       this.updateChart();      
      //       //console.log("ALLDATA->"+JSON.stringify(this.allData.pcsSum));
      //       // echarts查不到数据，将初始化echarts的方法全部放置到接口方法中即可。  
      // })  
      // 获取服务器的数据, 对this.allData进行赋值之后, 调用updateChart方法更新图表
      //console.log("ALLDATA->",JSON.stringify(res.data))
      //console.log("ALLDATA->",JSON.stringify(this.allData))
      this.updateChart();
    },
    updateChart() {
      // 处理图表需要的数据
      const dataOption = {
        xAxis: [
          {
            data: ['12:00', '13:00', '14:00', '15:00','16:00', '17:00', '18:00', '19:00','20:00', '21:00', '22:00', '23:00']
          }
        ],
        series: [
          {
            data: [0.2, 0.049, 0.07, 0.23, 0.25, 0.07, 0.15, 0.162, 0.32, 0.2, 0.06, 0.33]
          },
          {
            data: [2.6, 5.9, 9.0, 6.4, 8.7, 0.7, 5.6, 2.2, 0.4, 0.18, 0.24, 0.25]
          }
        ],         
      };
      // 图表数据变化配置
      this.chartInstance.setOption(dataOption);
    },
    screenAdapter() {
      this.titleFontSize = Math.round(this.$refs.refChart.offsetWidth / 50);
      const adapterOption = {
        title: {
          textStyle: {
            fontSize: this.titleFontSize,
          },
        },
        // 圆点分类标题
        legend: {
          textStyle: {
            fontSize: Math.round(this.titleFontSize / 1.2),
          }
        },
        xAxis: {
          //  改变x轴字体颜色和大小
          axisLabel: {
            textStyle: {
              fontSize: Math.round(this.titleFontSize * 0.8),
            },
          },
        },
        yAxis: [
          {
            // 字体大小
            nameTextStyle: {
              fontSize: this.titleFontSize
            },        
            //  改变y轴字体颜色和大小
            axisLabel: {
              textStyle: {
                fontSize: Math.round(this.titleFontSize * 0.8),
              },
            },
          },
          {
            // 字体大小
            nameTextStyle: {
              fontSize: this.titleFontSize
            },        
            //  改变y轴字体颜色和大小
            axisLabel: {
              textStyle: {
                fontSize: Math.round(this.titleFontSize * 0.8),
              },
            },
          }              
        ]
      
      };
      // 图表自适应变化配置
      this.chartInstance.setOption(adapterOption);
      this.chartInstance.resize();
    },
    // 定时器
    getDataTimer(){
      this.koiTimer = setInterval(() => {
        // 执行刷新数据的方法
        this.getData();
        //console.log("Hello World")
      }, 3000)
    },
    // 定时器
    getTootipTimer(){
      let index = 0; // 播放所在下标
      this.tootipTimer = setInterval(() => {
        // echarts实现定时播放tooltip
        this.chartInstance.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: index
        });
        index++;
        if(index > 12) {
          index = 0;
        }  
        //console.log("Hello World")
      }, 2000)
    },         
  }
}
</script>
<style lang='less' scoped>

</style>