<template>
      <div>
        <div>
          <!-- <span class="colorDeepskyblue" style="position: absolute; left: 10px;font-size: 10px;">时间：2022/07/01-2022/07/31</span>           -->
          <div class="colorGrass font-bold" :style="{'font-size': kFOne + 'px'}">🌈中国最值得去的旅游景点</div>
        </div>
      <div>
        <dv-capsule-chart :config="config" :style="{ width: kWOne + 'px',height: kHOne + 'px'}" />
      </div>
    </div>
</template>

<script>
// import { listAVPowerData } from "../../../api/home/index";
export default { 
  data() {
    return {
    // 获取浏览器可视区域高度（包含滚动条）、 window.innerHeight
    // 获取浏览器可视区域高度（不包含工具栏高度）、document.documentElement.clientHeight
    // 获取body的实际高度  (三个都是相同，兼容性不同的浏览器而设置的) document.body.clientHeight
    screenHeight: window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
    screenWidth: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
    chartInstance: null,
    allData: {
      aList: [
        {
          name: "布达拉宫",
          value: 975
        },
        {
          name: "丽江古城",
          value: 865
        },
        {
          name: "三峡大坝",
          value: 755
        },
        {
          name: "万里长城",
          value: 651
        },
        {
          name: "青海湖",
          value: 548
        },
        {
          name: "泰山",
          value: 435
        },
        {
          name: "西双版纳",
          value: 325
        },
        {
          name: "莫高窟",
          value: 218
        },
        {
          name: "三亚",
          value: 115
        },
        {
          name: "乌镇",
          value: 91
        }                                        
      ],
      bList: [
        {
          name: "九寨沟",
          value: 895
        },
        {
          name: "兵马俑",
          value: 885
        },
        {
          name: "五大连池",
          value: 774
        },
        {
          name: "三星堆遗址",
          value: 769
        },
        {
          name: "凤凰古城",
          value: 659
        },
        {
          name: "南京夫子庙",
          value: 648
        },
        {
          name: "可可西里",
          value: 534
        },
        {
          name: "呼伦贝尔草原",
          value: 527
        },
        {
          name: "新疆喀纳斯",
          value: 317
        },
        {
          name: "稻城亚丁",
          value: 301
        }
      ],
      cList: [
        {
          name: "大理洱海",
          value: 799
        },
        {
          name: "上海外滩",
          value: 788
        },
        {
          name: "泸沽湖",
          value: 677
        },
        {
          name: "珠穆朗玛峰",
          value: 666
        },
        {
          name: "少林寺",
          value: 555
        },
        {
          name: "峨眉山",
          value: 544
        },
        {
          name: "香格里拉",
          value: 433
        },
        {
          name: "承德避暑山庄",
          value: 422
        },
        {
          name: "故宫",
          value: 311
        },
        {
          name: "台湾日月潭",
          value: 300
        }
      ],
      dList: [
        {
          name: "张家界",
          value: 697
        },
        {
          name: "恒山",
          value: 687
        },
        {
          name: "香格里拉",
          value: 579
        },
        {
          name: "避暑山庄",
          value: 563
        },
        {
          name: "故宫",
          value: 457
        },
        {
          name: "黄果树瀑布",
          value: 442
        },
        {
          name: "黄山",
          value: 336
        },
        {
          name: "台湾日月潭",
          value: 325
        },
        {
          name: "青海湖",
          value: 211
        },
        {
          name: "普陀山",
          value: 200
        }
      ],
      eList: [
        {
          name: "长白山天池",
          value: 595
        },
        {
          name: "乐山大佛",
          value: 587
        },
        {
          name: "王者峡谷",
          value: 376
        },
        {
          name: "和平草丛",
          value: 362
        },
        {
          name: "QQ飞车赛道",
          value: 653
        },
        {
          name: "植物VS僵尸",
          value: 649
        },
        {
          name: "斗罗大陆",
          value: 737
        },
        {
          name: "OverLoad",
          value: 723
        },
        {
          name: "蟹黄堡",
          value: 616
        },
        {
          name: "青青草原",
          value: 403
        }
      ]
    },
    // 高度
    kHOne: null,
    // 宽度
    kWOne: null,
    // 标题字体
    kFOne: null,
    // 定时器监控
    koiTime: null,
    // 局部刷新定时器 
    koiTimer: null,
    // 翻页定时器
    timerId: null,    
    config: {
      showValue: true,
      data: []
    }
   }
 },
  beforeMount(){
    this.kWOne = Math.round(this.screenWidth * 0.23)
    this.kHOne = Math.round(this.screenHeight * 0.42)
    this.kFOne = Math.round(this.screenWidth / 120)
  },  
  mounted(){
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenWidth, false);
    // 鼠标移动时触发
    //window.addEventListener('mousemove',this.getHeight, false);
    this.resizeScreen();
    // 获取后端接口数据
    // this.getCurrentData();
    // 数据分组
    this.getData();
    // 执行局部数据刷新定时器
    // this.getDataTimer();
    // 数据切换定时器
    this.handleTimer();
  },
  destroyed() {
    // 清除自适应定时器
    clearInterval(this.koiTime);
    this.koiTime = null;
    // 清除局部刷新定时器
    clearInterval(this.koiTimer);
    this.koiTimer = null; 
    // 清除局部刷新定时器
    clearInterval(this.timerId);
    this.timerId = null;     
    // 页面大小改变时触发销毁
    window.removeEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发销毁
    window.removeEventListener('resize',this.getScreenWidth, false);
  }, 
  methods: {
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
        this.kHOne = Math.round(this.screenHeight * 0.42);
        //console.log("高度->"+this.screenHeight +"-"+ this.kHOne);
    },
    // 字体大小根据宽度自适应
    getScreenWidth(){
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      this.kWOne = Math.round(this.screenWidth * 0.23);
      this.kFOne = Math.round(this.screenWidth / 120);
      //console.log("宽度->"+this.screenWidth);
    },
    getCurrentData(){
        // 执行刷新数据的方法
        listAVPowerData().then(res => {
          this.allData = res.data;
        })
    },
    getData(value,unit) {
        //console.log("ALLDATA->",JSON.stringify(res.data)) 
        const { config } = this;
        // 将数据赋值给DataV的胶囊柱图
        if(value == null){
           this.config.data = this.allData.aList;
           this.config.unit = "次";
        } else {
          this.config.data = value;
          this.config.unit = unit;
        } 
        
        this.config = { ...this.config }
      // 获取服务器的数据, 对this.allData进行赋值之后, 调用updateChart方法更新图表
      //console.log("ALLDATA->",JSON.stringify(res.data))
      //console.log("ALLDATA->",JSON.stringify(this.allData))
    }, 
    // 数据刷新定时器
    getDataTimer(){
      this.koiTimer = setInterval(() => {    
        // 执行刷新数据的方法
        // listAVPowerData().then(res => {
        //   this.allData = res.data;
        // })
        // console.log("Hello World")
      }, 60000)
    },
    // 数据切换定时器
    handleTimer(){
      let index = 1;
      this.koiTimer = setInterval(() => {   
        index ++;
        if(index == 1){
          this.getData(this.allData.aList,"次");
        }else if(index == 2){
          this.getData(this.allData.bList,"刀");
        }else if(index == 3){
          this.getData(this.allData.cList,"两");
        }else if(index == 4){
          this.getData(this.allData.dList,"米");
        }else if(index == 5){
          this.getData(this.allData.eList,"斤");
        }else if(index > 5){
          index = 1;  
          this.getData(this.allData.aList);
        }    
      }, 2000)      
    }          
  },
}
</script>

<style lang="less" scoped>

</style>
