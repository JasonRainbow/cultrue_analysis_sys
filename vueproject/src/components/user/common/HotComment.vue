<script>
import {getHotCommentsByPage} from "../../../api/commentAPI";

  export default {
    name:'HotComment',
    props:{

    },
    data(){
      return{
        logoMap: {
          "豆瓣": require("../../../assets/platform_logo/douban.png"),
          "Youtube": require("../../../assets/platform_logo/youtube.png"),
          "IMDb": require("../../../assets/platform_logo/IMDb.png"),
          "Twitter": require("../../../assets/platform_logo/twitter.png"),
          "GoodReads": require("../../../assets/platform_logo/goodreads.jpg"),
          "烂番茄": require("../../../assets/platform_logo/rottentomoto.png"),
          "Facebook": require("../../../assets/platform_logo/facebook.png"),
          "Amazon": require("../../../assets/platform_logo/amazon.png"),
        },
        works:{
          records:{

          },
          total: 20,
          size: 10,
          current: 1,
          orders: [],
          optimizeCountSql: true,
          searchCount: true,
          countId: null,
          maxLimit: null,
          pages: 0,
        },
        searchParam2:{
          searchWorkName:null,
          searchContent:null,
          searchCountry:null,
          searchPlatform:null,
          searchTime:null,
          pageNum:1,
          pageSize:null
        },
        workName:null,
        commentContent:null,
        country:null,
        platform:null,
        time:null,

        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        }
      }
    },
    methods:{
      goBack(){
        console.log("返回");
      },
      handleChanged(val){
        this.searchParam2.pageNum=val;
        getHotCommentsByPage(this.searchParam2).then((res)=>{
          // console.log(res.data);
          if(res.code==="0"){
            this.works=res.data;
            // console.log(this.works)
          }
        })
      },
      search(){
        this.searchParam2.searchWorkName=this.workName;
        this.searchParam2.searchContent=this.commentContent;
        this.searchParam2.searchCountry=this.country;
        this.searchParam2.searchPlatform=this.platform;
        this.searchParam2.searchTime=this.time;
        // console.log(this.time);
        getHotCommentsByPage(this.searchParam2).then((res)=>{
          // console.log(res.data);
          if(res.code==="0"){
            this.works=res.data;
            // console.log(this.works)
          }
        })
      },
      Delete(){
        // console.log("delete success");
      }
    },
    mounted() {
      /*getCommentsByPage(this.searchParam2).then((res)=>{
        // console.log(res.data);
        if(res.code==="0"){
          this.works=res.data;
          // console.log(this.works.pages);
        }
      })*/
      this.search()
    }
  }


</script>

<template>
  <div class="div1" style="width:100%">
<!--    <el-page-header @back="goBack" content="详情页面" style="margin: auto">
    </el-page-header>-->
    <div style="margin-bottom: 10px;width: 100%">
      <el-form :inline="true" style="margin: 20px">
        <el-form-item label="搜索：">
          <el-input
            class="custom-input"
            v-model="workName"
            style="width: 130px"
            placeholder="请输入作品名"
            @keyup.enter.native="search"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input
            class="custom-input"
            v-model="platform"
            style="width: 150px"
            placeholder="请输入发布平台"
            @keyup.enter.native="search"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input
            class="custom-input"
            v-model="commentContent"
            style="width: 150px"
            clearable
            placeholder="请输入评论内容"
            @keyup.enter.native="search"
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input
            class="custom-input"
            v-model="country"
            style="width: 180px"
            clearable
            placeholder="请输入评论发布国家"
            @keyup.enter.native="search"
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-date-picker
            class="custom-select2"
            style="width: 180px"
            v-model="time"
            align="right"
            type="date"
            placeholder="选择评论发布日期"
            :picker-options="pickerOptions"
            @change="search"
            clearable
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd">
          </el-date-picker>
          <el-button type="primary" round plain style="margin-left: 20px" @click="search">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-for="(c,index) in works.records"
         :key="index"
         class="text item"
    >
      <el-card class="box-card2" shadow="hover">
        <div slot="header" class="clearfix">
          <span>{{c.monitorWork.name}}</span>
          <!--            <el-button style="float: right; padding: 3px 0" type="text" @click="Delete">删除</el-button>-->
        </div>
        <div class="text item">
          <div style="height:40px;width:53px;display: inline-block;vertical-align: top">
            <a :href="c.homeUrl" target="_blank">
              <img :src="c.avatar || logoMap[c.platform]" alt="头像"
                   :class="{'radius-avatar': c.avatar != null}"
                   style="height: 100%;width:40px;">
            </a>
          </div>
          <div style="display: inline-block;width: 93%;">
            <a :href="c.homeUrl" target="_blank">
              <span style="line-height: 40px" class="user-name">{{c.reviewer||"未知用户"}}</span>
            </a>

          <div class="div2">
            {{c.content}}
          </div>
          <div class="div3">
            <span class="sub-title">点赞数：</span>{{c.likes}}
          </div>
          <div class="div3">
            <span class="sub-title">情感：</span>{{c.sentiment==null?'未分析':c.sentiment}}
          </div>
          <div class="div3">
            <span class="sub-title">发布地：</span>{{c.country}}
          </div>
          <div class="div3">
            <span class="sub-title">发布平台：</span>{{c.platform}}
          </div>
          <div class="div3">
            <span class="sub-title">发布时间：</span>{{c.postTime}}
          </div>
          </div>
        </div>
      </el-card>
    </div>

    <el-pagination
      background
      layout="prev, pager, next"
      :page-count="works.pages"
      :page-size="works.size"
      @current-change="handleChanged" >
    </el-pagination>
  </div>
</template>

<style scoped>
@import "../../../assets/styles/mystyle.css";

  /*.div1{
    width:1050px;
    margin:30px auto auto auto;
  }*/

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: block;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .clearfix {
    color: #c15128;
    font-size: 18px;
  }

  .div3{
    display:inline-block;
    float:right;
    margin-left: 15px;
  }
  .div2{
    margin-bottom: 30px;
  }

  .box-card2 {
    background: url("../../../assets/img/card_bg.png");
  }

  .sub-title {
    color: #214ce7;
  }

  .user-name{
    //font-family: PingFang SC, HarmonyOS_Medium, Helvetica Neue, Microsoft YaHei, sans-serif;
    font-family: "微软雅黑","宋体";
    font-weight: 500;
    font-size: 16px;
    margin-right: 5px;
    cursor: pointer;
    color: #37a;
  }
  .radius-avatar {
    border-radius: 50%;
  }
</style>
