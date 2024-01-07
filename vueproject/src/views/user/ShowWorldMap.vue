<!--  -->
<template>
  <div id="index">
    <!--  DataV-Loading加载动画 -->
    <dv-loading v-if="loading">Loading...</dv-loading>
    <!-- 总体布局-start -->
    <div v-else class="koi-body">
      <!-- 第一部分-头部-start -->
      <div class="header">
        <!-- 首页 -->
        <span class="homePage font-bold colorDeepskyblue" :style="{'font-size': Math.round(this.screenWidth/100) + 'px', 'cursor': 'pointer'}" @click="goback">&lt;&lt;返回</span>
        <!-- 时间 -->
        <div class="localTime colorPink" :style="{'font-size': Math.round(this.screenWidth/100) + 'px'}">{{ dateYear }} {{ dateWeek }} {{ dateDay }}</div>
        <!-- 装饰10 -->
        <dv-decoration-10 class="dv-dec-10-left" />
        <!-- 装饰8 -->
        <dv-decoration-8 class="dv-dec-8-left" :color="decorationColor"/>
        <!-- 标题 -->
        <dv-decoration-7 class="title font-bold colorText" :style="{
        'width': screenWidth * 0.143 + 'px',
        'height': screenWidth * 0.024 + 'px',
        'margin-left': -screenWidth * 0.0306 + 'px',
        'font-size': screenWidth * 0.0131 + 'px'
        }">国际情感分布图</dv-decoration-7>
        <!--        <span class="title font-bold colorText" :style="{'font-size': Math.round(this.screenWidth/100) + 'px'}">传播效果大屏展示</span>-->
        <!-- 装饰8 -->
        <dv-decoration-8 class="dv-dec-8-right" :reverse="true" :color="decorationColor" />
        <!-- 装饰10 -->
        <dv-decoration-10 class="dv-dec-10-right"/>
      </div>
      <!-- Element-UI Layout布局 -->
      <div class="layoutHome">
        <el-row>
          <!--         左侧部分-->
          <el-col :span="24">
            <div :style="{ height: kHOne + 'px'}">
              <dv-border-box-12 style="padding:12px">
                <WorldMap :work-name="workName" :work-id="workId"></WorldMap>
              </dv-border-box-12>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 总体布局end -->
    </div>
  </div>
</template>

<script>
import { formatTime } from '../../utils/util.js';
// 导入图表组件
import WorldMap from "../../components/user/charts/WorldMap";

export default {
  name: 'ShowWorldMap',
  components: { // 注册组件
    WorldMap
  },
  data () {
    return {
      loading: true,
      // 装饰8颜色
      decorationColor: ['#568aea', '#000000'],
      // 时间
      timer: null,
      // 适应浏览器
      koiTime: null,
      // 定时跳转页面
      koiTimer: null,
      dateDay: null,
      dateYear: null,
      dateWeek: null,
      weekday: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
      // 获取浏览器可视区域高度（包含滚动条）、
      // 获取浏览器可视区域高度（不包含工具栏高度）、
      // 获取body的实际高度  (三个都是相同，兼容性不同的浏览器而设置的)
      screenHeight: window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
      screenWidth: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
      kHOne: 1000,
      workId: 1,
      workName: ''
    }
  },
  created () {

  },
  mounted(){
    this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenWidth, false);
    // 鼠标移动时触发
    //window.addEventListener('mousemove',this.getHeight, false);
    this.timeInterval();
    this.cancelLoading();
    this.resizeScreen();

  },
  beforeDestroy () {
    clearInterval(this.timer)
    this.timer = null;
    clearInterval(this.koiTime);
    this.koiTime = null;
    clearInterval(this.koiTimer);
    this.koiTimer = null;
    // 页面大小改变时触发
    window.removeEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.removeEventListener('resize',this.getScreenWidth, false);
  },
  watch: {
    screenWidth: function (news, old) {
      if (news <= 1200) {
        this.$notify({
          title: '浏览器高度提示',
          dangerouslyUseHTMLString: true,
          message: '<strong style= "color: teal">浏览器宽度<1200px将不再进行自适应</strong>',
          type: 'warning',
          position: 'bottom-right',
          duration: 2000
        });
      }
    },
    screenHeight: function (news, old) {
      if (news <= 700) {
        this.$notify({
          title: '浏览器宽度提示',
          dangerouslyUseHTMLString: true,
          message: '<strong style= "color: teal">浏览器高度<700px将不再进行自适应</strong>',
          type: 'warning',
          position: 'bottom-right',
          duration: 2000
        });
      }
    }
  },
  methods: {
    timeInterval() {
      this.timer = setInterval(() => {
        this.dateDay = formatTime(new Date(), 'HH: mm: ss')
        this.dateYear = formatTime(new Date(), 'yyyy/MM/dd')
        this.dateWeek = this.weekday[new Date().getDay()]
      }, 1000)
    },
    cancelLoading() {
      setTimeout(() => {
        this.loading = false
      }, 500)
    },
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
      this.kHOne = Math.round(this.screenHeight * 0.95);
      //console.log(this.screenHeight +"-"+ Math.round(this.percentHThirty) +"-"+ Math.round(this.percentHForty));
    },
    // 字体大小根据宽度自适应
    getScreenWidth(){
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      //console.log("hh-"+this.screenWidth+"-"+this.screenHeight);
    },
    goback() {
      console.log("返回")
      // this.$router.push({path: "/big-screen"})
      this.$router.back()
    },
  },
  beforeMount() {
    this.workName = this.$route.query.workName
    this.workId = this.$route.query.workId
    console.log(this.workId)
    console.log(this.workName)
  }
}
</script>

<style lang="less" scoped>

</style>

