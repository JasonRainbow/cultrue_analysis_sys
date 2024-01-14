<!--  -->
<template>
  <div id="index">
    <!--  DataV-Loading加载动画 -->
    <dv-loading v-if="loading">Loading...</dv-loading>
    <!-- 总体布局-start -->
    <div v-else class="koi-body">
      <!-- 第一部分-头部-start -->
      <div class="header">
        <!-- 时间 -->
        <div class="homePage colorPink" :style="{'font-size': Math.round(this.screenWidth/100) + 'px'}">{{ dateYear }} {{ dateWeek }} {{ dateDay }}</div>
        <!-- 返回 -->
        <span class="localTime font-bold colorDeepskyblue" :style="{'font-size': Math.round(this.screenWidth/85) + 'px', 'cursor': 'pointer'}" @click="goback">返回首页&gt;&gt;</span>
        <!-- 装饰10 -->
        <dv-decoration-10 class="dv-dec-10-left" />
        <!-- 装饰8 -->
        <dv-decoration-8 class="dv-dec-8-left" :color="decorationColor"/>
        <!-- 标题 -->
        <dv-decoration-7 class="title font-bold colorText" :style="{
        'width': screenWidth * 0.178 + 'px',
        'height': screenWidth * 0.0196 + 'px',
        // 'margin-left': -screenWidth * 0.0306 + 'px',
        'font-size': screenWidth * 0.0171 + 'px'
        }">传播效果大屏展示</dv-decoration-7>
        <!--        <span class="title font-bold colorText" :style="{'font-size': Math.round(this.screenWidth/100) + 'px'}">传播效果大屏展示</span>-->
        <!-- 装饰8 -->
        <dv-decoration-8 class="dv-dec-8-right" :reverse="true" :color="decorationColor" />
        <!-- 装饰10 -->
        <dv-decoration-10 class="dv-dec-10-right"/>
      </div>
      <!-- 第一部分-头部-end -->

      <!-- 网格布局 grid -->
      <!-- <div class="wrapper">
        <div class="one item">One</div>
        <div class="two item">Two</div>
        <div class="three item">Three</div>
        <div class="four item">Four</div>
        <div class="five item">Five</div>
        <div class="six item">Six</div>
      </div> -->

      <!-- 弹性布局 flex -->
      <!-- <div class="container">
        <div class="one item">One</div>
        <div class="two item">Two</div>
        <div class="three item">Three</div>
        <div class="four item">Four</div>
        <div class="five item">Five</div>
        <div class="six item">Six</div>
      </div> -->

      <!-- Element-UI Layout布局 -->
      <div class="layoutHome">
        <el-row>
          <!--         左侧部分-->
          <el-col :span="7">
            <div :style="{ height: kHOne + 'px'}">
              <dv-border-box-12 :style="{
              'padding-top':screenWidth * 0.0052 + 'px',
               'padding-left': screenWidth * 0.0066 + 'px',
               'padding-right': screenWidth * 0.0066 + 'px',
               'font-size': screenWidth * 0.0105 + 'px',
               'width':'100%'
              }">
                各国整体情感排名
                <SentimentScrollChart :work-id="workId" :key="workId"></SentimentScrollChart>
              </dv-border-box-12>
            </div>

            <div :style="{ height: kHTwo + 'px'}">
              <!-- style="padding:12px" -->
              <dv-border-box-12 style="padding:12px;width:100%">
                <RelationGraph :work-id="workId" :key="workId"></RelationGraph>
              </dv-border-box-12>
            </div>
            <div :style="{ height: kHTen + 'px'}">
              <!-- style="padding:12px" -->
              <dv-border-box-12 style="padding:6px;width:100%">
                <MessageChart :work-id="workId" :key="workId"></MessageChart>
              </dv-border-box-12>
            </div>
          </el-col>

          <!--          中间部分-->
          <el-col :span="9">
            <div :style="{ height: kHThree + 'px'}">
              <dv-border-box-12 style="padding:12px;width:100%;height:100%">
                <div style="margin-bottom: 5px;height:100%">
                  <span :style="{'margin-right': '8px', 'height': '45%', 'font-size': screenWidth * 0.0118 + 'px'}" class="font-bold">监测作品切换：</span>
                  <el-select class="custom-select" v-model="workId" :size="inputSize" placeholder="请选择作品类型" style="height: 45%"  @change="selectChanged">
                    <el-option
                      v-for="work in works"
                      :key="work.id"
                      :label="work.name"
                      :value="work.id"
                    />
                  </el-select>
                  <!--                  <el-button type="success" plain>世界情感分布图</el-button>-->
                  <div @click="sentimentMap" style="margin:5px auto auto auto;width: 50%;height:45%">
                    <router-link :to="{path: '/worldMap', query: {workId: workId, workName: getWorkName(workId)}}">
                      <dv-border-box-8 id="btn-world"

                                       :style="{
                        'color': 'white',
                        'cursor': 'pointer', 'width': '100%',
                         'border-radius': '10px',
                         'height': screenWidth * 0.0261 + 'px',
                         'margin': 'auto auto',
                          'font-size': screenWidth * 0.0116 + 'px',
                           'text-align': 'center',
                           'line-height': screenWidth * 0.0261 + 'px',
                                       }" :reverse="true">
                        <span :style="{}">查看世界情感分布图</span>
                      </dv-border-box-8>
                    </router-link>

                  </div>
                </div>
              </dv-border-box-12>
            </div>
            <div :style="{ height: kHFour + 'px'}">
              <dv-border-box-12 style="padding:5px;width: 100%">
                <Score :work-id="workId" :key="workId"></Score>
              </dv-border-box-12>
            </div>
            <div :style="{ height: kHNine + 'px'}">
              <dv-border-box-12 style="padding-top:10px;width:100%">
                <!--               <Story></Story>-->
                <Subject :work-id="workId" :key="workId"></Subject>
              </dv-border-box-12>
            </div>
          </el-col>

          <!--          右侧部分-->
          <el-col :span="8">
            <div :style="{ height: kHSix + 'px'}">
              <dv-border-box-12 style="padding:12px;width:100%">
                <WordCloud :work-id="workId" :key="workId"></WordCloud>
              </dv-border-box-12>
            </div>
            <div :style="{ height: kHSeven + 'px'}">
              <dv-border-box-12 style="padding:12px;width:100%">
                <PolarityCapsuleChart :work-id="workId" :key="workId"></PolarityCapsuleChart>
              </dv-border-box-12>
            </div>
            <div :style="{ height: kHEight + 'px'}">
              <dv-border-box-12 style="padding:12px;width:100%">
                <!--                <MessageChart :work-id="workId" :key="workId"></MessageChart>-->
                <DataSourcePieChart :work-id="workId" :key="workId"></DataSourcePieChart>
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
import SentimentScrollChart from "./charts/SentimentScrollChart";
import PolarityCapsuleChart from "./charts/PolarityCapsuleChart";
import WordCloud from "./charts/WordCloud";
import RelationGraph from "./charts/RelationGraph";
import DataSourcePieChart from "./charts/DataSourcePieChart";
import MessageChart from "./charts/MessageChart";
import Subject from "./charts/Subject";
import Score from "./charts/Score";
import {recordUserSelect} from "../../api/userAPI";
import {getMonitorWorkByUserId} from "../../api/monitor_workAPI";


export default {
  name: 'BigScreen',
  components: {
    Subject,
    // 注册组件
    Score,
    SentimentScrollChart,
    PolarityCapsuleChart,
    WordCloud,
    RelationGraph,
    DataSourcePieChart,
    MessageChart,
  },
  data () {
    return {
      userId: null,
      inputSize: 'small',
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
      kHOne: 200,
      kHTwo: 500,
      kHThree: 240,
      kHFour: 300,
      kHFive: 450,
      kHSix: 400,
      kHSeven: 400,
      kHEight: 400,
      kHNine: 370,
      kHTen:320,
      workId: 1, // 选中的作品
      works: [ // 所有的作品
        {
          id: 1,
          name: "流浪地球"
        }
      ]
    }
  },
  created () {
    this.getUser()
    this.getWorkData()
  },
  mounted(){
    this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (this.screenWidth < 1920) {
      this.inputSize = "small"
    } else {
      this.inputSize = "medium"
    }
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenWidth, false);
    // 鼠标移动时触发
    //window.addEventListener('mousemove',this.getHeight, false);
    this.timeInterval();
    this.cancelLoading();
    this.resizeScreen();
    // this.selectChanged();
    // console.log(this.screenWidth)
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
    getWorkName(id) {
      let idx = this.works.map((item)=>{
        return item.id
      }).indexOf(id)
      return this.works[idx].name
    },
    getWorkData: function () {
      let userId = null;
      let loginUser = localStorage.getItem("user");
      if (loginUser) {
        loginUser = JSON.parse(loginUser) // 解析存储在浏览器中的用户数据
        userId = loginUser.id
      }
      getMonitorWorkByUserId({userId: userId}).then((res)=>{ // 获取监测作品
        if (res.code === "0") {
          this.works = res.data.map((item)=>{
            return {id: item.id, name: item.name}
          })
          this.workId = this.works[0].id // 默认选中第一个作品
        } else {
          console.log(res.msg)
        }
      })
    },
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
      this.kHOne = Math.round(this.screenHeight * 0.24);
      this.kHTwo = Math.round(this.screenHeight * 0.46);  //0.4397
      this.kHThree = Math.round(this.screenHeight * 0.13);  //0.18
      this.kHFour = Math.round(this.screenHeight * 0.42); //0.76 0.44
      // this.kHFive = Math.round(this.screenHeight * 0.385);
      this.kHSix = Math.round(this.screenHeight * 0.28);
      this.kHSeven = Math.round(this.screenHeight * 0.28);
      this.kHEight = Math.round(this.screenHeight * 0.39);
      this.kHNine = Math.round(this.screenHeight * 0.4);
      this.kHTen = Math.round(this.screenHeight * 0.245);
      //console.log(this.screenHeight +"-"+ Math.round(this.percentHThirty) +"-"+ Math.round(this.percentHForty));
    },
    // 字体大小根据宽度自适应
    getScreenWidth(){
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      //console.log("hh-"+this.screenWidth+"-"+this.screenHeight);
    },
    sentimentMap() {
      console.log("世界情感分布图")
    },
    goback() {
      console.log("返回")
      this.$router.push({path: "/home"})
    },
    selectChanged(){
        if (!this.userId) return // 用户没有登录，不记录浏览
        recordUserSelect({userId: this.userId, workId:this.workId}).then((res)=>{ // 获取监测作品
            if (res.code === "0") {
               console.log("记录成功")
            } else {
                console.log(res.msg)
            }
        })
    },
    getUser() {
      let loginUser = localStorage.getItem("user");
      if (loginUser) {
        loginUser = JSON.parse(loginUser) // 解析存储在浏览器中的用户数据
        this.userId = loginUser.id
      }
    }
  }
}
</script>

<style lang="css" scoped>

.custom-select /deep/ .el-input__inner {
  line-height: 46px;
  height: 36px;
  color: #fdefd2;
  font-size: 16px;
  text-align: center;
  opacity: 1;
  border: none;
  background: url("../../assets/img/nav2_bg.png") center center no-repeat;
  font-weight: bold;
}

.custom-select /deep/ .el-input__inner {
  padding-right: 10px;
}

.custom-select /deep/ .el-select__caret {
  color: #f0ae4b;
  font-weight: 1000;
  font-size: 20px;
}

</style>
