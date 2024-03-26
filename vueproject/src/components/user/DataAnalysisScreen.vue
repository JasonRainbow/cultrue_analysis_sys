<template>
  <vuescroll style="background-color:#081832 ">
    <div style="background-color: #081832">
      <div :style="{'height':this.screenHeight+'px','background-color':'#081832'}">
        <header class="header left" :style="{'height':this.screenHeight * 0.0912+'px'}">
          <div class="left nav" :style="{'height':this.screenHeight * 0.0697+'px'}">
            <ul>
              <!--          <a href="" @click="selected()">数据概览</a>-->
              <li :class='isActive === "数据概览" ? "nav_active":""' :style="{'margin-top':screenHeight * 0.01609 + 'px'}"><i class="nav_1"></i><a :style="{'font-size':this.screenHeight * 0.01502+'px'}" href="javascript:void(0);" @click="selected($event)">数据概览</a> </li>
              <!--    index.html    -->
              <li :class='isActive === "情感地图" ? "nav_active":""' :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                <i class="nav_2"></i>
                <!--            情感地图-->
                <router-link :to="{path: '/worldMap', query: {workId: workId, workName: getWorkName(workId)}}">
                  <a :style="{'font-size':this.screenHeight * 0.01502+'px'}" href="javascript:void(0);" @click="selected($event)" style="color: #FFFFFF">情感地图</a>
                </router-link>
              </li>
              <!--   carContrl.html     -->
              <li :class='isActive === "作品画像" ? "nav_active":""' :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                <i class="nav_3" ></i>
                <router-link :to="{path: '/WorkPortraitPage', query: {workId: workId}}">
                  <a :style="{'font-size':this.screenHeight * 0.01502+'px'}" href="javascript:void(0);" @click="selected($event)" style="color: #FFFFFF">作品画像</a>
                </router-link>
              </li>
              <!--     map.html   -->



            </ul>
          </div>
          <div class="header_center left" :style="{'height':this.screenHeight * 0.0804+'px','margin-top':screenHeight * 0.01072 + 'px'}">
            <h2 :style="{'height':this.screenHeight * 0.0295+'px','margin-top':screenHeight * 0.009 + 'px'}"><strong :style="{'font-size':this.screenHeight * 0.02682+'px'}">传播效果智能分析</strong></h2>
            <p class="color_font" :style="{'height':this.screenHeight * 0.01931+'px'}"><small :style="{'font-size':this.screenHeight * 0.01073+'px'}">Intelligent analysis of communication effect</small></p>
          </div>
          <div class="right nav text_right" :style="{'height':this.screenHeight * 0.0697+'px'}">
            <ul>
              <li :class='isActive === "作品预测" ? "nav_active":""' :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                <i class="nav_4"></i>
                <router-link :to="{path: '/WorkEffectPredict', query: {workId: workId}}">
                  <a :style="{'font-size':this.screenHeight * 0.01502+'px'}" href="javascript:void(0);" @click="selected($event)" style="color: #FFFFFF">作品预测</a>
                </router-link>
              </li>
              <!--  static.html      -->
              <li :class='isActive === "信息录入" ? "nav_active":""' :style="{'margin-top':screenHeight * 0.01609 + 'px'}"><i class="nav_5" ></i>
                <a :style="{'font-size':this.screenHeight * 0.01502+'px'}" href="javascript:void(0);" @click="selected($event)">信息录入
                </a>
              </li>
              <!--  message.html      -->
              <li :class='isActive === "表格界面" ? "nav_active":""' :style="{'margin-top':screenHeight * 0.01609 + 'px'}"><i class="nav_6"></i><a :style="{'font-size':this.screenHeight * 0.01502+'px'}" href="javascript:void(0);" @click="selected($event)">表格界面</a> </li>
              <!--        table1.html-->
            </ul>
          </div>
        </header>

        <div class="con left" :style="{'height':this.screenHeight * 0.9345+'px'}">
          <!--选择时间-->
          <el-row :gutter="40" :style="{'height':this.screenHeight * 0.0645+'px'}">
            <el-col :span="8" style="height: 100%">
              <div class="static_top left" style="height: 59%" :style="{'line-height':screenHeight * 0.03862 + 'px'}">
                <i></i><span :style="{'font-size':this.screenHeight * 0.01716+'px'}">总体概况</span>
              </div>
            </el-col>
            <el-col :span="8" style="text-align: center">
            <span
              :style="{
                      'margin-right': '8px',
                      'height': '45%',
                      'font-size': screenWidth * 0.0118 + 'px',
                      'margin-left': '30px',
                      'color':'#ffffff'
                    }"
              class="font-bold border-font">
                      监测作品切换：
                    </span>
              <el-select class="custom-select" v-model="workId" :size="inputSize" placeholder="请选择作品类型" :style="{'height': screenHeight * 0.03862 + 'px'}"  @change="selectChanged">
                <el-option
                  v-for="work in works"
                  :key="work.id"
                  :label="work.name"
                  :value="work.id"
                >
                  &nbsp;&nbsp;{{work.name}} <span class="commentCnt_tick">{{work.commentCnt}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="8">
            <span class="localTime font-bold colorDeepskyblue"
                  :style="{'cursor': 'pointer','float':'right','margin-right':'10%'}"
                  @click="goBack">返回首页&gt;&gt;</span>
            </el-col>
          </el-row>
          <!--数据总概-->
          <div class="con_div" :style="{'height':this.screenHeight * 0.118+'px'}">
            <el-row :gutter="25" style="height: 100%;">
              <el-col :span="8" style="height: 100%">
                <div class="con_div_text01 left">
                  <img src="../../assets/DataAnalysisScreen/info_1.png" class="left text01_img">
                  <div class="left text01_div" :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                    <p :style="{'font-size':this.screenHeight * 0.01394+'px','line-height':screenHeight * 0.03755 + 'px'}">爬取评论总数量</p>
  <!--                  <p>{{allNum}}</p>-->
  <!--                  <p><countTo :startVal='startVal' :endVal='endVal' :duration='3000' autoplay="true"></countTo></p>-->
                    <p :style="{'line-height':screenHeight * 0.03755 + 'px'}"><animate-number :style="{'font-size':this.screenHeight * 0.03004+'px'}" :to="allNum" :from="0" :duration="2000" :key="allNum"></animate-number></p>

                  </div>
                </div>
                <div class="con_div_text01 right">
                  <img src="../../assets/DataAnalysisScreen/info_2.png" class="left text01_img">
                  <div class="left text01_div" :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                    <p :style="{'font-size':this.screenHeight * 0.01394+'px','line-height':screenHeight * 0.03755 + 'px'}">今日爬取评论数量</p>
                    <p :style="{'line-height':screenHeight * 0.03755 + 'px'}"><animate-number :style="{'font-size':this.screenHeight * 0.03004+'px'}" :to="todayNum" :from="0" :duration="2000" :key="todayNum"></animate-number></p>
                  </div>
                </div>
              </el-col>
              <el-col :span="8" style="height: 100%">
                <div class="con_div_text01 left">
                  <img src="../../assets/DataAnalysisScreen/info_4.png" class="left text01_img">
                  <div class="left text01_div" :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                    <p :style="{'font-size':this.screenHeight * 0.01394+'px','line-height':screenHeight * 0.03755 + 'px'}">分析国家总数量</p>
                    <p class="sky" :style="{'line-height':screenHeight * 0.03755 + 'px'}"><animate-number :style="{'font-size':this.screenHeight * 0.03004+'px'}"  :to="countryNum" :from="0" :duration="1000" :key="countryNum"></animate-number></p>
                  </div>
                </div>
                <div class="con_div_text01 right">
                  <img src="../../assets/DataAnalysisScreen/info_5.png" class="left text01_img">
                  <div class="left text01_div" :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                    <p :style="{'font-size':this.screenHeight * 0.01394+'px','line-height':screenHeight * 0.03755 + 'px'}">分析平台总数量</p>
                    <p class="sky" :style="{'line-height':screenHeight * 0.03755 + 'px'}"><animate-number :style="{'font-size':this.screenHeight * 0.03004+'px'}" :to="platformNum" :from="0" :duration="500" :key="platformNum"></animate-number></p>
                  </div>
                </div>
              </el-col>
              <el-col :span="8" style="height: 100%">
                <div class="con_div_text01 left">
                  <img src="../../assets/DataAnalysisScreen/info_6.png" class="left text01_img">
                  <div class="left text01_div" :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                    <p :style="{'font-size':this.screenHeight * 0.01394+'px','line-height':screenHeight * 0.03755 + 'px'}">评论涉及语种数量</p>
                    <p class="org" :style="{'line-height':screenHeight * 0.03755 + 'px'}"><animate-number :style="{'font-size':this.screenHeight * 0.03004+'px'}" :to="languageNum" :from="0" :duration="500" :key="languageNum"></animate-number></p>
                  </div>
                </div>
                <div class="con_div_text01 right">
                  <img src="../../assets/DataAnalysisScreen/info_7.png" class="left text01_img">
                  <div class="left text01_div" :style="{'margin-top':screenHeight * 0.01609 + 'px'}">
                    <p :style="{'font-size':this.screenHeight * 0.01394+'px','line-height':screenHeight * 0.03755 + 'px'}">评论时间跨度(天)</p>
                    <p class="org" :style="{'line-height':screenHeight * 0.03755 + 'px'}"><animate-number :style="{'font-size':this.screenHeight * 0.03004+'px'}" :to="daysBetween" :from="0" :duration="2000" :key="daysBetween"></animate-number></p>
                    <div :style="{'font-size': screenHeight * 0.01072+'px','color': '#ff4e4e !important'}">({{firstCommentDate}}至{{lastCommentDate}})</div>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
          <!--统计分析图-->

          <div class="div_any" :style="{'height':this.screenHeight * 0.6545+'px'}">
            <el-row :gutter="20" style="height: 100%">
              <el-col :span="7" style="height: 100%">
                <div class="div_any_child" :style="{'height': '45.9%','margin-top':screenHeight * 0.02682 + 'px'}">
                  <div class="div_any_title" :style="{'height': '12.5%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_1.png">作品评分图</div>
                  <!--                    <p id="char1" class="p_chart"></p>-->
                  <Score :work-id="workId" :key="workId"></Score>
                </div>
                <div class="div_any_child" :style="{'height': '45.9%','margin-top':screenHeight * 0.02682 + 'px'}">
                  <div class="div_any_title" :style="{'height': '12.5%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_2.png">国际总体信息量变化趋势图</div>
                  <!--                    <p id="char2" class="p_chart"></p>-->
                  <MessageChart :work-id="workId" :key="workId"></MessageChart>
                </div>
              </el-col>
              <el-col :span="10" style="height: 100%">
                <div class="div_any_child div_height" :style="{'height': '95.9%','margin-top':screenHeight * 0.02682 + 'px'}">
                  <div class="div_any_title any_title_width" :style="{'height': '5.982%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_3.png">词云图</div>
                  <!--                    <div id="map_div"></div>-->
                  <WordCloud :work-id="workId" :key="workId" :height="'80%'"></WordCloud>
                </div>
              </el-col>
              <el-col :span="7" style="height: 100%">
                <div class="div_any_child" :style="{'height': '45.9%','margin-top':screenHeight * 0.02682 + 'px'}">
                  <div class="div_any_title" :style="{'height': '12.5%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_4.png">国家情感排名</div>
                  <!--                    <p id="char3" class="p_chart"></p>-->
                  <!--                    <Subject :work-id="workId" :key="workId"></Subject>-->
                  <SentimentScrollChart :work-id="workId" :key="workId"></SentimentScrollChart>
                </div>
                <div class="div_any_child" :style="{'height': '45.9%','margin-top':screenHeight * 0.02682 + 'px'}">
                  <div class="div_any_title" :style="{'height': '12.5%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_5.png">国家情感分布</div>
                  <!--                    <p id="char4" class="p_chart"></p>-->
                  <PolarityCapsuleChart :work-id="workId" :key="workId"></PolarityCapsuleChart>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
      <div>
        <div class="div_table">
          <el-row :gutter="30">
            <el-col :span="8">
<!--                480px-->
              <div class="div_any_child" :style="{'height':this.screenHeight * 0.515+'px'}">
                <div class="div_any_title" :style="{'height': '7.291%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_4.png">共现语义网络图</div>
                <div class="table_p">
                  <RelationGraph :work-id="workId" :key="workId"></RelationGraph>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="div_any_child" :style="{'height':this.screenHeight * 0.515+'px'}">
                <div class="div_any_title" :style="{'height': '7.291%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_4.png">数据来源占比图</div>
                <div class="table_p">
                  <DataSourcePieChart :work-id="workId" :key="workId"></DataSourcePieChart>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="div_any_child" :style="{'height':this.screenHeight * 0.515+'px'}">
                <div class="div_any_title" :style="{'height': '7.291%','font-size':screenHeight * 0.01716 + 'px'}"><img src="../../assets/DataAnalysisScreen/title_4.png">主题情感分析</div>
                <div class="table_p">
                  <Subject :work-id="workId" :key="workId"></Subject>
                  <!--                          <table>-->
                  <!--                              <thead><tr>-->
                  <!--                                  <th>排名</th>-->
                  <!--                                  <th>车牌号</th>-->
                  <!--                                  <th>时速（km）</th>-->
                  <!--                              </tr>-->
                  <!--                              </thead>-->
                  <!--                              <tbody>-->
                  <!--                              <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>-->
                  <!--                              <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>-->
                  <!--                              <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>-->
                  <!--                              <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>-->
                  <!--                              <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>-->
                  <!--                              </tbody>-->
                  <!--                          </table>-->
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </vuescroll>

</template>
<script>
import {recordUserSelect} from "../../api/userAPI";
import WordCloud from "./DataScreen/WordCloud.vue";
import Score from "./DataScreen/Score.vue";
import MessageChart from "./DataScreen/MessageChart.vue";
import Subject from "./DataScreen/Subject.vue";
import DataSourcePieChart from "./DataScreen/DataSourcePieChart.vue";
import {getMonitorWorkByUserId} from "../../api/monitor_workAPI";
import RelationGraph from "./DataScreen/RelationGraph.vue";
import PolarityCapsuleChart from "./DataScreen/PolarityCapsuleChart.vue";
import SentimentScrollChart from "./DataScreen/SentimentScrollChart.vue";
import {getCommentOverallMessage} from "../../api/commentAPI";
import vuescroll from "vuescroll";
import countTo from 'vue-count-to';
export default {
  name:"DataAnalysisScreen",
  components: {
    countTo,
    vuescroll,
      WordCloud,
      Score,
      MessageChart,
      Subject,
      DataSourcePieChart,
      RelationGraph,
      PolarityCapsuleChart,
      SentimentScrollChart
  },
  data(){
    return {
        screenHeight:window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
        startVal:0,
        endVal:this.allNum,
        userId:'',
        workName:null,
        country:null,
        title:null,
        workId:2,
        inputSize: 'small',
        isActive:"数据概览",
        allNum:0,
        todayNum:0,
        platformNum:0,
        countryNum:0,
        languageNum:0,
        daysBetween:0,
        firstCommentDate: '',
        lastCommentDate: '',
        screenWidth: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
        works: [
            {
                id:2,
                name:'流浪地球1'
            },
            {
                id:1,
                name:'水浒传'
            }
        ]
    }
  },
  methods:{
       getWorkOverallMessage(){
         getCommentOverallMessage({workId:this.workId}).then((res)=>{
           if(res.code === '0'){
             this.allNum = res.data.allNum
             this.todayNum = res.data.todayNum
             this.platformNum = res.data.platformNum
             this.countryNum = res.data.countryNum
             this.languageNum = res.data.languageNum
             this.daysBetween = res.data.daysBetween
             this.firstCommentDate = res.data.firstCommentDate
             this.lastCommentDate = res.data.lastCommentDate
           }
         })
       },
      selectChanged(){
          this.getWorkOverallMessage()
          if (!this.userId) return // 用户没有登录，不记录浏览
          recordUserSelect({userId: this.userId, workId:this.workId}).then((res)=>{ // 获取监测作品
              if (res.code === "0") {
                  console.log("记录成功")
              } else {
                  // console.log(res.msg)
              }
          })
      },
      // 导航条添加样式
      selected(event) {
          // console.log(event)
          this.isActive = event.target.innerText
      },
      test(){

      },
      sentimentMap() {
        // console.log("世界情感分布图")
      },
      goBack() {
        // console.log("返回")
        this.$router.push({path: "/home"})
      },
      getUser() {
        let loginUser = localStorage.getItem("user");
        if (loginUser) {
          loginUser = JSON.parse(loginUser) // 解析存储在浏览器中的用户数据
          this.userId = loginUser.id
        }
      },
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
              return {id: item.id, name: item.name, commentCnt: item.commentCnt}
            })
            this.workId = this.works[0].id // 默认选中第一个作品
          } else {
            // console.log(res.msg)
          }
        })
      },
    // 字体大小根据宽度自适应
    getScreenSize(){
      this.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      this.screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
      console.log(this.screenWidth,'*',this.screenHeight)
    },
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
  },
  computed: {
    // screenHeight(){
    //   console.log("屏幕高度变化了")
    //   return (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) + 'px'
    // }
  },
  beforeMount() {

  },
  mounted(){
    window.addEventListener('resize',this.getScreenSize, false);
  },
  created () {
    this.getUser()
    this.getWorkData()
    this.getWorkOverallMessage()
  },
  beforeDestroy() {
    window.removeEventListener('resize',this.getScreenSize)
  }
}
</script>
<style lang="css" scoped>
/*
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
 */
*{
  margin: 0;
  padding: 0;
  list-style: none;
  text-decoration: none;
}
body{
  font-size: 100%;
}
.header{
  width: 100%;
  height: 85px;
  background-color: #030829;

}
.left{
  float: left;
}
.right{
  float: right;
}
.text_right{
  text-align: right;
}
.nav{
  width: 35%;
}
/* 总体概况样式*/
.nav_active{
  border-bottom: 4px solid #4b8df8;
  box-shadow: -10px 0px 15px #034c6a inset,
  0px -10px 15px #034c6a inset,
  10px 0px 15px #034c6a inset,
  0px 10px 15px #034c6a inset;

  box-sizing: border-box;
}

/* 标题下小字样式*/
.color_font{
  color: #e8f7fe !important;
  font-size: 14px !important;
}
/*顶部导航栏图片设置 */
.nav_1{
  background-image: url("../../assets/DataAnalysisScreen/nav_1.png");
}
.nav_2{
  background-image: url("../../assets/DataAnalysisScreen/nav_2.png");
}
.nav_3{
  background-image: url("../../assets/DataAnalysisScreen/nav_3.png");
}
.nav_4{
  background-image: url("../../assets/DataAnalysisScreen/nav_7.png");
}
.nav_5{
  background-image: url("../../assets/DataAnalysisScreen/nav_9.png");
}
.nav_6{
  background-image: url("../../assets/DataAnalysisScreen/nav_4.png");
}
/*
.nav_5{
  background-image: url("../../../assets/DataAnalysisScreen/nav_5.png");
}
.nav_6{
  background-image: url("../../../assets/DataAnalysisScreen/nav_6.png");
}
*/


/* 顶部导航样式*/
.nav>ul>li{
  display: inline-block;
  width: 120px;
  text-align: center;
  height: 50px;
  position: relative;
  line-height: 50px;
  margin-top: 15px;
  box-sizing: border-box;
  border-radius: 5px;
}
/* 顶部导航鼠标放上去样式*/
.nav>ul>li:hover{
  box-shadow: -10px 0px 15px #034c6a inset,
  0px -10px 15px #034c6a inset,
  10px 0px 15px #034c6a inset,
  0px 10px 15px #034c6a inset;

  box-sizing: border-box;
}
/* 顶部导航补充样式以及i标签样式*/
.nav>ul>li i{
  width: 16px;
  height: 16px;
  display: inline-block;
  position: relative;
  top:3px;
  margin-right: 5px;
}
/* 顶部导航内部a标签样式*/
.nav>ul>li>a{
  color: #ffffff;
  font-size: 14px;
}
/* 顶部标题样式*/
.header_center{
  width: 30%;
  //margin: 0px auto;
  color: #FFFFff;
  text-align: center;
  height: 75px;
  background-image: url("../../assets/DataAnalysisScreen/logoBg.png");
  background-size: 100% 100%;
  font-family: "微软雅黑"!important;

  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 18px;
}
/* 顶部标题样式*/
h2{
  margin-top: 10px;
  margin-bottom: 10px;;
  font-size: 25px;
}
/*
.header_center h2{
  margin-top: 10px !important;
  margin-bottom: 10px; !important;
  font-size: 18px !important;
}
*/

/* 总体概况样式*/
.con{
  width: 100%;
  background-color: #081832;
  padding-top: 20px;
  padding-bottom: 20px;
}
.select_time{
  width: 140px;
  height: 36px;

  margin-bottom: 25px;
  margin-left: 1%;
  padding-left: 20px;
;

}
.static_top{
  width: 140px;
  height: 35px;
  line-height: 36px;
  background-color: #04425f;
  margin-bottom: 25px;
  margin-left: 7%;
  padding-left: 20px;
  border-radius: 1px;
  box-shadow: 0px 3px 3px #61d2f7;
}
.select_time img{
  height: 18px;
  margin-top: 9px;
}
.select_time input{
  border: none;
  background-color: transparent;
  width: 80px;
  height: 20px;
  top:-5px;
  margin-left: 10px;
  position: relative;
  text-indent: 1em;
  outline: none;
}
/* 12345字体颜色*/
.red{
  color: red !important;
}
.sky{
  color: #25f3e6 !important;
}
.org{
  color: #ff4e4e !important;
}

.con_div_text{
  height: 100%;
  background-color: #034c6a;
  width: 32%;
  margin-right: 1.3%;
}
.con_div_text01{
  width: 50%;
  height: 100%;
  background-color: #034c6a;
}
.text01_img{
  width: 40px;
  height: 40px;
  margin-left: 5%;
  margin-top: 35px;
}
.text01_div{
  margin-top: 15px;
  margin-left: 5%;
  text-align: center;

}
.text01_div p{
  line-height: 35px;
}
.text01_div p:nth-child(1){
  font-size: 13px;
  color: #ffffff;
}
.text01_div p:nth-child(2){
  font-size: 28px;
  color: #ffff43;
  font-weight: 600;

}
.con_div{
  height: 110px;
  width: 98%;
  //margin-left: 1%;
    margin: auto auto 25px auto;
  //margin-bottom: 25px;
}

/*统计分析界面样式*/
.static_top i{
  width: 16px;
  height: 16px;
  display: inline-block;
  position: relative;
  background-image: url("../../assets/DataAnalysisScreen/nav_7.png");
  background-repeat: no-repeat;
  margin-right: 6px;
  top:1px;
}
.static_top span{
  color: #25f3e6;
}

/* 中间5张图*/
.div_any{
  width: 98%;
  margin-left: 1%;
  margin-bottom: 25px;
  height: 610px;
}
.div_any01{
  width: 23%;
  margin-right: 2%;
}
.div_any02{
  width: 48%;
  margin-right: 2%;
}
.div_any03{
  width: 98%;
  margin: 15px auto;
}
.div_height{
  //height:586px !important;
}
.any_title_width{
  width: 30% !important;
  left: 35% !important;
}


/* 左侧三张图*/
/* 整个盒子*/
.div_table{
  width: 98%;
  margin-left: 1%;
  margin-bottom: 25px;
}
.div_table_box{
  width: 23%;
  margin-right: 2%;
}
.div_any_child{
  width: 100%;
  height: 280px;
  box-shadow: -10px 0px 15px #034c6a inset,
  0px -10px 15px #034c6a inset,
  10px 0px 15px #034c6a inset,
  0px 10px 15px #034c6a inset;
  border: 1px solid #034c6a;
  box-sizing: border-box;
  position: relative;
  margin-top: 25px;
}
.div_any_title{
  background-color: #034c6a;
  border-radius: 18px;
  position: absolute;
  height: 35px;
  width: 60%;
  top:-15px;
  color: #ffffff;
  font-weight: bold;
  font-size: 16px;
  left: 20%;
  line-height: 35px;
  text-align: center;
}
.table_p{
  height: 93%;
  margin-top: 7%;
  position: relative;
}

.table_p table{
  width: 100%;
  height: 100%;
  border-collapse: collapse;

  position: absolute;
  text-align: center;
}
.table_p table thead th{
  color: #61d2f7;
  font-size: 14px;
  font-weight: 600;
  padding-top: 5px;
  padding-bottom: 5px;
}
.table_p table tbody{
  color: #ffffff;
  font-size: 13px;
}
.table_p table tbody tr:nth-child(2n+1){
  background-color: #072951;
  box-shadow: -10px 0px 15px #034c6a inset,
  10px 0px 15px #034c6a inset;
}
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
.commentCnt_tick {
  float: right;
  color: #e88226;
}
</style>
