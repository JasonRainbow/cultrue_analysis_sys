<template>
  <div style="margin: 20px auto; height: 100%; width: 90%">
<!--    <h2>{{platform+"极性评论排名"}}</h2>-->
    <div style="width: 100%;" >
      <el-select v-model="platform" placeholder="请选择" @change="selectChanged" >
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div style="margin-top: 15px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card class="box-card bg">
            <div slot="header" class="clearfix">
              <span style="font-weight: bold">积极评论</span>
            </div>
            <div v-show="positiveCommentList.length>0">
            <div v-for="(item,id) in positiveCommentList" :key="id" class="text item" style="text-align: left;font-size: medium">
              <div :style="{'padding-top': (id==0 ? (0+'px'):(5+'px'))}">
                {{id+1+'.'+(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
              </div>
              <div style="float: right">
                <img src="../../../assets/img/zan.svg">
                <span>{{item.likes}}</span>
                <span @click="enterDetails(item.id)" class="details">查看详情</span>
              </div>
            </div>
            </div>
            <div v-show="positiveCommentList.length==0" style="margin-top: 15%">
              <h2>暂无数据</h2>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card bg">
            <div slot="header" class="clearfix">
              <span style="font-weight: bold">消极评论</span>
            </div>
            <div v-show="negativeCommentList.length>0">
            <div v-for="(item,id) in negativeCommentList" :key="id" class="text item" style="text-align: left;font-size: medium">
              <div :style="{'padding-top': (id==0 ? (0+'px'):(5+'px'))}">
                {{id+1+'.'+(item.content.length>41 ? item.content.slice(0,41)+'...':item.content)}}
              </div>
              <div style="float: right">
                <img src="../../../assets/img/zan.svg">
                <span>{{item.likes}}</span>
                <span @click="enterDetails(item.id)" class="details">查看详情</span>
              </div>
            </div>
            </div>
            <div v-show="negativeCommentList.length==0" style="margin-top: 15%">
              <h2>暂无数据</h2>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {getPlatformByWorkIdAndCountry} from "../../../api/commentAPI";
import {getPolarityComment} from "../../../api/commentAPI";
export default {
  name: "PolarityCommentList",
  props: {//用于组件通信
    workId: {
      type: Number,
      require: true
    },
    country:{
      type:String,
      require:true
    },
    width: {
      type: String,
      require: false,
      default: "1000px"
    },
    height: {
      type: String,
      require: false,
      default: "500px"
    },
  },
  computed:{
    height2(){
      return this.height !== "400px" ? this.height : "400px"
    }
  },
  data() {
    return {
      positiveCommentList:[],
      negativeCommentList:[],
      platform:'豆瓣',
      options: [{
        value: 1,
        label: '近一年'
      }, {
        value: 2,
        label: '近两年'
      },{
        value: 3,
        label: '近三年'
      },
        {
          value: 4,
          label: '近四年'
        },
        {
          value: 5,
          label: '近五年'
        }],

      params: {
        country:this.country,
      },
      searchParams:{
        country:this.country,
        platform:this.platform,
        size:10
      }
    }
  },
  created() {
    if(this.workId!==0){
      this.params.workId=this.workId
    }
    getPlatformByWorkIdAndCountry(this.params).then((res)=>{
      if(res.code==='0'){
        let selectList = []
        for(let i=0;i<res.data.length;i++){
          let option={}
          option.value=res.data[i]
          option.label=res.data[i]
          selectList.push(option)
        }
        this.options=selectList
      }else{
        alert("加载数据出错!")
      }
    })
  },
  mounted() {
    this.getData()
  },
  beforeDestroy() {
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    enterDetails(commentId){
      this.$bus.$emit('showCommentDetails',commentId)
    },
    selectChanged(){
      this.getData()
    },
    getData() {//获取数据
      //调用接口
      if(this.workId !== 0){
        this.searchParams.workId = this.workId
      }
      this.searchParams.country=this.country
      this.searchParams.platform=this.platform
      getPolarityComment(this.searchParams).then((res)=>{
        if(res.code==='0'){
          this.positiveCommentList=res.data.positive
          this.negativeCommentList=res.data.negative
        }else{
          alert("请求数据失败!")
        }
      })
    },
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple-light {
  background: #e5e9f2;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
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
  width: 480px;
  min-height: 500px;
  overflow: auto;
}
.box-card::-webkit-scrollbar{
  display: none;
}

.details{
  cursor: pointer;
  color: #7dbfff;
}

.bg {
  width: 100%;
  height: 340px;
  background: url("../../../assets/img/detail_bg.svg"),linear-gradient(225deg, rgba(21,152,244,0.05) 0%, rgba(255,215,108,0.05) 100%);
  background-repeat: repeat;
}
</style>
