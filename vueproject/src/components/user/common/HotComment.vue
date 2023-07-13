<script>
import {getHotCommentsByPage} from "../../../api/commentAPI";

  export default {
    name:'HotComment',
    props:{

    },
    data(){
      return{
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
          console.log(res.data);
          if(res.code==="0"){
            this.works=res.data;
          }
        })
      },
      search(){
        this.searchParam2.searchWorkName=this.workName;
        this.searchParam2.searchContent=this.commentContent;
        this.searchParam2.searchCountry=this.country;
        this.searchParam2.searchPlatform=this.platform;
        this.searchParam2.searchTime=this.time;
        console.log(this.time);
        getHotCommentsByPage(this.searchParam2).then((res)=>{
          console.log(res.data);
          if(res.code==="0"){
            this.works=res.data;
          }
        })
      },
      Delete(){
        console.log("delete success");
      }
    },
    mounted() {
      /*getCommentsByPage(this.searchParam2).then((res)=>{
        console.log(res.data);
        if(res.code==="0"){
          this.works=res.data;
          console.log(this.works.pages);
        }
      })*/
      this.search()
    }
  }


</script>

<template>
  <div class="div1">
<!--    <el-page-header @back="goBack" content="详情页面" style="margin: auto">
    </el-page-header>-->
    <div>
      <table style="margin: auto">
        <tr>
          <td>请输入作品名：</td>
          <td><el-input v-model="workName" placeholder="请输入内容"></el-input></td>
          <td>请输入评论内容：</td>
          <td><el-input v-model="commentContent" placeholder="请输入内容"></el-input></td>
        </tr>
        <tr>
          <td>请输入评论发布国家</td>
          <td><el-input v-model="country" placeholder="请输入内容"></el-input></td>
          <td>请输入评论发布平台：</td>
          <td><el-input v-model="platform" placeholder="请输入内容"></el-input></td>
        </tr>
        <tr>
          <td>请输入评论发布时间</td>
          <td><!--<el-input v-model="time" placeholder="请输入内容"></el-input>-->
            <el-date-picker
              v-model="time"
              align="right"
              type="date"
              placeholder="选择日期"
              :picker-options="pickerOptions"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </td>
          <td>确定：</td>
          <td><el-button type="primary" round plain style="margin-left: 20px" @click="search">查询</el-button></td>
        </tr>
      </table>
    </div>
    <el-card class="box-card1" style="margin: auto">
      <div slot="header" class="clearfix">
        <span>评论详情</span>
<!--        <el-button style="float: right; padding: 3px 0" type="text">批量处理</el-button>-->
      </div>
      <div v-for="(c,index) in works.records" :key="index" class="text item">

        <el-card class="box-card2" shadow="hover">
          <div slot="header" class="clearfix">
            <span>{{c.monitorWork.name}}</span>
<!--            <el-button style="float: right; padding: 3px 0" type="text" @click="Delete">删除</el-button>-->
          </div>
          <div class="text item">
            <div class="div2">
              {{c.content}}
            </div>
            <div class="div3">
              点赞数：{{c.likes}}
            </div>
            <div class="div3">
              情感：{{c.sentiment==null?'未分析':c.sentiment}}
            </div>
            <div class="div3">
              发布地：{{c.country}}
            </div>
            <div class="div3">
              发布平台：{{c.platform}}
            </div>
            <div class="div3">
              发布时间：{{c.postTime}}
            </div>
            </div>
        </el-card>


      </div>
    </el-card>

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

  .box-card1{
    width: 1000px;
  }
  .div3{
    display:inline-block;
    float:right;
    margin-left: 15px;
  }
  .div2{
    margin-bottom: 30px;
  }
  table{
    width:800px;
    padding-left: 10px;
  }
</style>
