<template>
  <vuescroll>
    <div id="main">
      <div class="bg">
        <div class="workName-show">
          <i></i>
          <div style="font-size: 20px; color: #e2652f">
            湖湘文化作品({{work.subCategory}})：
            <span style="color: #ef0000">{{work.name}} ({{work.englishName}})</span>
            <span style="margin-left: 40px;">热点评论</span>
          </div>
        </div>
        <div @click="goBack" class="back" >
          返回>>
        </div>
        <div style="width: 95%;margin: 10px auto auto auto">
          <el-row :gutter="25" style="margin: auto; display: block">
            <el-col v-show="commentDetail['豆瓣']" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg">
                <div slot="header" class="clearfix">
                  <span class="title">豆瓣</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail['豆瓣']" v-show="commentDetail['豆瓣']" :key="id" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right; margin-top: 10px;">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>
                <div v-show="!commentDetail['豆瓣']" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail.Youtube" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg">
                <div slot="header" class="clearfix">
                  <span class="title">Youtube</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail.Youtube" v-show="commentDetail.Youtube" :key="id" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>
                <div v-show="!commentDetail.Youtube" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail.Twitter" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg" >
                <div slot="header" class="clearfix">
                  <span class="title">Twitter</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail.Twitter" v-show="commentDetail.Twitter" :key="id" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>
                <div v-show="!commentDetail.Twitter" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail.IMDb" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg" >
                <div slot="header" class="clearfix">
                  <span class="title">IMDb</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail.IMDb" v-show="commentDetail.IMDb" :key="id" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>

                <div v-show="!commentDetail.IMDb" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail['烂番茄']" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg">
                <div slot="header" class="clearfix">
                  <span class="title">烂番茄</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail['烂番茄']" v-show="commentDetail['烂番茄']" :key="id" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>

                <div v-show="!commentDetail['烂番茄']" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail.Facebook" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg">
                <div slot="header" class="clearfix">
                  <span class="title">Facebook</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail.Facebook" v-show="commentDetail.Facebook" :key="id" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>

                <div v-show="!commentDetail.Facebook" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail.Amazon" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg">
                <div slot="header" class="clearfix">
                  <span class="title">Amazon</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail.Amazon" v-show="commentDetail.Amazon" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>

                <div v-show="!commentDetail.Amazon" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
            <el-col v-show="commentDetail.GoodReads" :xs="24" :md="12" :lg="8">
              <el-card class="box-card bg">
                <div slot="header" class="clearfix">
                  <span class="title">GoodReads</span>
                </div>
                <div class="comment-list">
                  <div v-for="(item,id) in commentDetail.GoodReads" v-show="commentDetail.GoodReads" class="text item">
                    <div class="comment-body">
                      <span class="comment-no">{{id+1+'.'}}</span>
                      {{(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
                    </div>
                    <div style="float: right">
                      <img alt="" src="../../assets/img/zan.svg">
                      <span>{{item.likes}}</span>
                      <span @click="enterDetails(item.id)" class="details">查看详情</span>
                    </div>
                  </div>
                </div>

                <div v-show="!commentDetail.GoodReads" style="min-height: 200px;text-align: center;margin-top: 10%">
                  暂无数据
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </vuescroll>

</template>

<script>

import {getHighCommentByPlatform} from "../../api/commentAPI";
import vuescroll from "vuescroll";
import {getMonitorWorkById} from "../../api/monitor_workAPI";


export default {
  name: "HighImpactComment",
  components: {vuescroll},
  props: {
    /*workId: {
      type: Number,
      require: true
    }*/
  },
  data() {
    return {
      commentDetail: {},
      params:{
        workId:this.$route.query.workId,
        size:10
      },
      work: {
        workName: "",
        englishName: ""
      }
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    enterDetails(commentId){
      console.log(commentId,"commentId")
      this.$router.push({
        path: '/commentDetail',
        query: {
          commentId: commentId
        }
      })
    },
    getWorkData() {
      getMonitorWorkById(this.params.workId).then((res)=>{
        if (res.code === "0") {
          // console.log(res)
          this.work = res.data
          // console.log(this.work)
        }
      })
    }
  },
  created() {
    getHighCommentByPlatform(this.params).then((res)=>{
      if(res.code==='0'){
        this.commentDetail=res.data
        console.log(this.commentDetail,'comment')
      }
    })
    this.getWorkData()
  },
}
</script>

<style scoped lang="css">
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  padding-bottom: 5px;
  border-bottom: 2px dashed #9d9898;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
  border-radius: 10%;
  max-height: 400px;
  background: url("../../assets/img/detail_bg.svg"),
  linear-gradient(225deg, rgba(21,152,244,0.05) 0%, rgba(255,215,108,0.05) 100%);
}

.comment-list {
  width: 100%;
  max-height: 18em;
  overflow: auto;
}

.comment-list::-webkit-scrollbar{
    display: none;
}

.comment-body {
  width: 70%;
}

.comment-no {
  color: #da681d;
  font-weight: bold;
}

.box-card:hover {
    box-shadow: 0 16px 32px 0 rgba(48, 55, 66, 0.15);
    transform: translate(0,-5px);
    transition-delay: 0s !important;
}

/deep/ .el-row {
  /*display:flex;
  flex-wrap: wrap;*/
}

/deep/ .el-row  .el-card {
  min-width: 100%;
  height: 350px;
  transition: all .5s;
}


.back{
  font-size: 20px;
  color: red;
  font-weight: bold;
  cursor: pointer;
  width: 75px;
  position: absolute;
  right: 10px;
  top: 10px;
}

.bg {
  width: 100%;
  margin-top: 25px;
  margin-bottom: 25px;
}

.details{
  color: #7dbfff;
  cursor: pointer;
}

.title{
  font-weight: bold;
}

#main {
  background: white url("../../assets/img/detail_bg.svg") center center repeat;
  position: absolute;
  width: 100%;
}

.workName-show {
  font-weight:bolder;
  position: absolute;
  width: 90%;
  top: 10px;
  left: 10px;
}
.workName-show i{
  margin-right: 10px;
  width: 42px;
  height: 42px;
  display: inline-block;
  float: left;
  background: url("../../assets/img/hunanLogo.png") center center;
}

</style>
