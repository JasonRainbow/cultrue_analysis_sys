<template>
  <vuescroll>
    <div class="container">
      <div class="content">
        <div class="back-btn" @click="goBack">返回>></div>
        <div class="main-top" style="margin-top: 20px;">
          <el-row :gutter="15" type="flex">
            <el-col :span="17">
              <el-card class="workInfo-card" style="height:100%">
                <div style="display: flex;align-items: center;">
                  <img class="work-img" :src="work.imgUrl" alt="作品图片">
                  <div class="simple-info-field">
                    <div><span class="label-span">作品名称：</span><span style="color: #c15128;">{{this.work.name}} ({{this.work.englishName}})</span></div>
                    <div><span class="label-span">作者/导演：</span><span>{{this.work.author}}</span></div>
                    <div><span class="label-span">发行时间：</span><span>{{this.work.postTime}}</span></div>
                    <div><span class="label-span">作品评分(均分)：</span>
                      <el-rate
                        class="rate-field"
                        v-model="value"
                        disabled
                        show-score
                        text-color="#ff9900"
                        score-template="{value}"
                      >
                      </el-rate>
                    </div>
                    <div class="briefIntro"><span class="label-span">作品简介：</span><span>{{this.work.content}}</span></div>
                    <div> <span style="float: right; margin-top: 0.5em">
                    <ViewTranslation
                      class="view-btn"
                      :work-name="this.work.name"
                      :translated-content="this.work.translatedContent"
                      :content="this.work.content"
                    >
                    </ViewTranslation>
                    <a class="view-btn" :href="this.work.citeUrl" target="_blank">查看详情</a>
                    <span class="label-span">(信息来源: <a class="platform-a" :href="this.platform.platformUrl" target="_blank">{{this.platform.platformName}}</a>)</span>
                    <span class="view-btn" @click="enterComment">查看热点评论</span>
                  </span>
                    </div>
                  </div>
                </div>
                <div class="complex-info-field">
                  <el-row :gutter="100">
                    <el-col :span="12"><span class="label-span">一级类别：</span><span>{{this.work.category}}</span></el-col>
                    <el-col :span="12"><span class="label-span">二级类别：</span><span>{{this.work.subCategory}}</span></el-col>
                  </el-row>
                  <el-row :gutter="100">
                    <el-col :span="12"><span class="label-span">已采集评论数：</span><span>{{this.work.commentCnt}}</span></el-col>
                    <el-col :span="12"><span class="label-span">已采集评论的点赞总数：</span><span>{{this.work.commentLikesCnt}}</span></el-col>
                  </el-row>
                  <el-row :gutter="100">
                    <el-col :span="12"><span class="label-span">传播效果综合得分：</span><span>{{this.effectScore}}</span></el-col>
                    <el-col :span="12">
                      <span class="label-span">得分等级：</span>
                      <span>{{this.scoreLevel}}</span>
                      <div style="display: inline">
                        <el-tooltip effect="dark" placement="top-start">
                          <div slot="content" v-html="toolTip"></div>
                          <i class="el-icon-question"></i>
                        </el-tooltip>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row :gutter="100">
                    <el-col :span="24" class="popularity-cause"><span class="label-span">受欢迎原因：</span><span>{{this.work.popularityCause}}</span></el-col>
                  </el-row>
                </div>
              </el-card>
            </el-col>
            <el-col :span="7">
              <el-card class="sameCategoryCmp-card" style="height:100%">
                <SameCategoryWorkCompareChart :work-id="workId"></SameCategoryWorkCompareChart>
              </el-card>
            </el-col>
            <div style="clear:both"></div>
          </el-row>
        </div>
        <div class="main-medium" style="margin-top: 20px;">
          <el-row :gutter="15" type="flex">
            <el-col :span="17">
              <el-card class="sentiment-card" style="height:100%">
                <el-row :gutter="20">
                  <el-col :span="8"><PolarityDynamicRingChart  :work-id="workId"></PolarityDynamicRingChart></el-col>
                  <el-col :span="8"><WorkPositiveHighFreqTable :work-id="workId"></WorkPositiveHighFreqTable></el-col>
                  <el-col :span="8"><WorkNegativeHighFreqTable :work-id="workId"></WorkNegativeHighFreqTable></el-col>
                </el-row>
              </el-card>
            </el-col>
            <el-col :span="7">
              <el-card class="coreUser-card" style="height:100%">
                <CoreUserAnalysis :work-id="workId"></CoreUserAnalysis>
              </el-card>
            </el-col>
            <div style="clear:both"></div>
          </el-row>
        </div>
        <div class="main-bottom" style="margin-top: 20px;">
          <el-card class="subject-card">
            <el-row :gutter="20">
              <el-col :span="9"><SubjectPropertyTable :work-id="workId"></SubjectPropertyTable></el-col>
              <el-col :span="15"><SubjectCategoryBar :work-id="workId"></SubjectCategoryBar></el-col>
            </el-row>
          </el-card>
        </div>
      </div>
    </div>
  </vuescroll>
</template>

<script>
import ViewTranslation from "../../components/user/common/ViewTranslation";
import vuescroll from "vuescroll";
import PolarityDynamicRingChart from "../../components/user/charts/PolarityDynamicRingChart";
import WorkPositiveHighFreqTable from "../../components/user/common/WorkPositiveHighFreqTable";
import WorkNegativeHighFreqTable from "../../components/user/common/WorkNegativeHighFreqTable";
import SubjectPropertyTable from "../../components/user/common/SubjectPropertyTable";
import SubjectCategoryBar from "../../components/user/charts/SubjectCategoryBar";
import SameCategoryWorkCompareChart from "../../components/user/charts/SameCategoryWorkCompareCapsuleChart";
import CoreUserAnalysis from "../../components/user/common/CoreUserAnalysis";
import {getMonitorWorkById} from "../../api/monitor_workAPI";
import {workScoreByWorkId} from "../../api/WorkScoreAPI";
import {findWorkEffectScore} from "../../api/WorkEffectScoreAPI";

export default {
  name: "WorkPortraitPage",
  components:{
    SameCategoryWorkCompareChart,
    SubjectCategoryBar,
    SubjectPropertyTable,
    WorkPositiveHighFreqTable,
    WorkNegativeHighFreqTable,
    PolarityDynamicRingChart,
    ViewTranslation,
    CoreUserAnalysis,
    vuescroll},
  data() {
    return {
      workId: Number(this.$route.query.workId),
      value: 3.6, // 作品评分
      toolTip:"传播效果得分<br/>低于60&nbsp;&nbsp;不及格<br/>60-69&nbsp;&nbsp;&nbsp;&nbsp;及格<br/>" +
        "70-79&nbsp;&nbsp;&nbsp;&nbsp;中等<br/>80-89&nbsp;&nbsp;&nbsp;&nbsp;良好<br/>90-100&nbsp;&nbsp;优秀<br/>",
      work: {},
      platform: {
        platformUrl: 'https://www.imdb.com/',
        platformName: "IMDb"
      },
      scoreLevel: '',
      effectScore:0
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      // 获取作品的相关介绍
      getMonitorWorkById(this.workId).then((res) => {
        if (res.code === "0") {
          this.work = res.data
          this.platform = res.data.platform
        }
      })
      // 获取作品评分(平均得分)
      workScoreByWorkId({workId: this.workId}).then((res) => {
        if (res.code === "0") {
          res.data.forEach(item => {
            if (item.platform === "平均") {
              this.value = item.score
            }
          })
        }
      })
      findWorkEffectScore({workId: this.workId}).then((res) => {
        if (res.code === '0') {
          if (res.data === null) {
            this.effectScore = "暂无数据"
          } else {
            this.effectScore = res.data.effectScore.toFixed(2)
            this.rateScoreLevel(this.effectScore)
          }
        }else{
          alert("后台出现错误，请联系管理员")
        }
      })
    },
    // 评定得分等级
    rateScoreLevel(effectScore) {
      if(effectScore<60) {
        this.scoreLevel = '不及格'
      }else if(effectScore>=60&&effectScore<70) {
        this.scoreLevel = '及格'
      }else if(effectScore>=70&&effectScore<80) {
        this.scoreLevel = '中等'
      }else if(effectScore>=80&&effectScore<90) {
        this.scoreLevel = '良好'
      }else if(effectScore>=90&&effectScore<=100) {
        this.scoreLevel = '优秀'
      }
    },
    goBack() {
      this.$router.back()
    },
    enterComment(){
      this.$router.push(
        {
          path: '/HighImpactComment',
          query: {
            workId: this.work.id,
            workName: this.work.name
          }
        }
      )
    }
  }
}

</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  background:url('../../assets/img/card_bg2.jpg')
}
.content {
  width: 98%;
  margin-top: 50px;
}
.el-card {
  background: url('../../assets/img/card_bg.png');
}
.work-img {
  width: 220px;
  height: 220px;
  border-radius: 50%;
  float: left
}
.simple-info-field {
  float: right;
  width: 80%;
  font-size: 18px;
  font-weight: bold;
}
.el-rate {
  display: inline;
}
/deep/ .el-rate .el-rate__item:first-child {
  margin-left: -50px;
}
.briefIntro {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4;
}
.simple-info-field div {
  margin: 5px 40px;
}
.label-span {
  color: #cccccc;
}
.popularity-cause {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4;
}
.view-btn {
  color: #2569b0;
  cursor: pointer;
  font-size: 18px;
  margin-left: 15px;
}
.view-btn:hover {
  text-decoration: underline;
  color: #c15128;
}
.platform-a:hover {
  text-decoration: underline;
  color: #c15128;
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.complex-info-field {
  margin-top: 30px;
  font-size: 18px;
  font-weight: bold;
}
.back-btn {
  font-size: 20px;
  color: red;
  font-weight: bold;
  cursor: pointer;
  width: 75px;
  position: absolute;
  right: 10px;
  top: 10px;
}
</style>
