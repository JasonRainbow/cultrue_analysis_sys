<template>
  <div id="center">
    <!-- 方块显示图 -->
    <div class="square">
      <!-- 正常 -->
      <div
        class="bg-color-black item"
        :style="{ height: kHOne + 'px'}"
        v-for="(item,index) in row1"
        :key="index + 100"
      >
        <p
          class="colorDarkturquoise font-bold"
          :style="{ 'font-size': kFOne + 'px','text-align': 'left','margin-left': kFOne + 'px'}"
          v-text="item.name"
        ></p>
        <p :style="{ height: Math.round(kFOne/3) + 'px'}"></p>
        <p
          :style="{ height: kHOne + 'px','font-size': Math.round(kFOne*2) + 'px','text-align': 'left','margin-left': kFOne + 'px','margin-top': Math.round(kFOne/2) + 'px', 'color': item.color}"
          v-text="item.value"
        ></p>
      </div>

      <!-- 异常 -->
      <div
        class="bg-color-black item"
        :style="{ height: kHOne + 'px'}"
        v-for="(item,index) in row2"
        :key="index + 200"
      >
        <p
          class="colorLightsteelblue font-bold"
          :style="{ 'font-size': kFOne + 'px','text-align': 'left','margin-left': kFOne + 'px'}"
          v-text="item.name"
        ></p>
        <p :style="{ height: Math.round(kFOne/1.5) + 'px'}"></p>
        <p
          :style="{ height: kHOne + 'px','font-size': Math.round(kFOne*2) + 'px','text-align': 'left','margin-left': kFOne + 'px','margin-top': Math.round(kFOne/2) + 'px', 'color': item.color}"
          v-text="item.value"
        ></p>
      </div>
    </div>
  </div>
</template>

<script>
// import { getCurrentData, getCurrentDataByStorage } from "@/api/ems/index";
export default {
  name: "centerBox",
  components: {},
  data() {
    return {
      // 获取浏览器可视区域高度（包含滚动条）、 window.innerHeight
      // 获取浏览器可视区域高度（不包含工具栏高度）、document.documentElement.clientHeight
      // 获取body的实际高度  (三个都是相同，兼容性不同的浏览器而设置的) document.body.clientHeight
      screenHeight:
        window.innerHeight ||
        document.documentElement.clientHeight ||
        document.body.clientHeight,
      screenWidth:
        window.innerWidth ||
        document.documentElement.clientWidth ||
        document.body.clientWidth,
        row1: [],
        row2: [],
      koiTime: null,
      koiTimer: null,
      kHOne: null,
      kFOne: null,
    };
  },
  created() {},
  beforeMount() {
    this.kHOne = Math.round(this.screenHeight * 0.07);
  },
  mounted() {
    // 页面大小改变时触发
    window.addEventListener("resize", this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener("resize", this.getScreenWidth, false);
    // 鼠标移动时触发
    //window.addEventListener('mousemove',this.getHeight, false);
    this.resizeScreen();
    this.getData();
    // 定时器
    this.getDataTimer();
  },
  destroyed() {
    // 清除多次执行定时器
    clearInterval(this.koiTime);
    this.koiTime = null;
    // 清除多次执行定时器
    clearInterval(this.koiTimer);
    this.koiTimer = null;
    // 页面大小改变时触发
    window.removeEventListener("resize", this.getScreenHeight, false);
    // 页面大小改变时触发
    window.removeEventListener("resize", this.getScreenWidth, false);
  },
  methods: {
    resizeScreen() {
      this.koiTime = setInterval(() => {
        this.getScreenHeight();
        this.getScreenWidth();
      }, 200);
    },
    // 获取浏览器高度进行自适应
    getScreenHeight() {
      this.screenHeight =
        window.innerHeight ||
        document.documentElement.innerHeight ||
        document.body.clientHeight;
      // 四舍五入取整数
      this.kHOne = Math.round(this.screenHeight * 0.07);
      //console.log("高度->"+this.screenHeight +"-"+ this.kHOne);
    },
    // 字体大小根据宽度自适应
    getScreenWidth() {
      this.screenWidth =
        window.innerWidth ||
        document.documentElement.clientWidth ||
        document.body.clientWidth;
      this.kFOne = Math.round(this.screenWidth / 150);
      //console.log("宽度->"+this.screenWidth);
    },
    getData() {
      this.row1 = [];
      this.row1.push({ name: 'column1', value: "正常", color: '#33cea0'})
      this.row1.push({ name: 'column2', value: "正常", color: '#33cea0'})
      this.row1.push({ name: 'column3', value: "故障", color: 'tomato' })
      this.row1.push({ name: 'column4', value: "故障", color: 'tomato' })

      let num1 = this.randomNum(10000,99999);
      let num2 = this.randomNum(10000,99999);
      let num3 = this.randomNum(10000,99999);
      let num4 = this.randomNum(10000,99999);
      this.row2 = [];
      this.row2.push({ name: 'column5', value: num1, color: '#33cea0'})
      this.row2.push({ name: 'column6', value: num2, color: '#33cea0'})
      this.row2.push({ name: 'column7', value: num3, color: '#33cea0'})
      this.row2.push({ name: 'column8', value: num4, color: '#33cea0'})         
      // getCurrentData().then((res) => {
      //   //console.log("ALLDATA->",JSON.stringify(res.data))
      //   this.row1 = res.data.row1;
      //   this.row2 = res.data.row2;      
      //   });
      // 获取服务器的数据, 对this.allData进行赋值之后, 调用updateChart方法更新图表
      //console.log("ALLDATA->",JSON.stringify(res.data))
      //console.log("ALLDATA->",JSON.stringify(res.allData))
    },
    // 定时器
    getDataTimer() {
      this.koiTimer = setInterval(() => {
        this.getData();
        //console.log("Hello World")
      }, 3000);
    },
  },
};
</script>

<style lang="less" scoped>
#center {
  display: flex;
  flex-direction: column;
  .square {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    .item {
      // 控制方块宽度比例
      width: 24.5%;
      border-radius: 6px;
      margin-top: 0.5%;
      margin-bottom: 0.5%;
    }
  }
}
</style>
