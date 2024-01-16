<template>
<!--  <div id="app">
    <el-row style="display: flex; justify-content: center">
      <el-col :span="16" :xs="24">
        <el-card class="box-card grid-content animate__animated animate__bounceIn" shadow="always">
          <div style="margin-top: 20px; font-size: 20px; text-indent: 2em; font-family: SimSun-ExtB;">
            <p>
              为了方便从宏观上查看近几年各国对于我国语言文化作品的情感态度，
              我们设计了该模块用于统计最近一年各国从整体上对中国的情感，并用图表的形式展现出来，可以点击下面的卡片查看详情。
            </p>
          </div>
          <el-row :gutter="20">
            <el-col v-for="item in countries" :key="item" :span="6" :xs="12" style="margin-top: 20px">
              <router-link :to="{path:'/sentiment-assessment',query: { country: item}}">
                <el-card class="item-card">{{item}}</el-card>
              </router-link>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>-->
  <div id="app" style="margin-top: 2%">
    <div style="margin-left: 1%;float: left;width:15%;margin-top: 1.5%">
      <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;margin-left: 0">
        <el-radio-button :label="false">展开</el-radio-button>
        <el-radio-button :label="true">收起</el-radio-button>
      </el-radio-group>
      <el-menu default-active="1-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse" @select="handleSelect"
               :unique-opened="true">
        <el-submenu index="1-1">
          <template slot="title">
            <i class="el-icon-location book"></i>
            <span slot="title">文学作品</span>
          </template>
          <div style="height:500px">
          <el-scrollbar style="height:100%" id="el-scrollbar__wrap">
          <div v-for="(value,key,index) in categoryAndWorks['文学']" :key="index">
            <el-menu-item-group>
              <span slot="title">{{key}}</span>
              <div  v-for="(item,index1) in value" :key="index1">
                <el-menu-item :index="item.id+'-'+item.name">
<!--                  :style="{color:((item.count === 0 || item.count==='0') ? '#FF0000' : '#000')}"-->
                  <div>
                    <span>{{item.name}}</span>
                    <span style="color: #7dbfff">{{'('+item.count+')'}}</span>
                  </div>
                </el-menu-item>
              </div>
            </el-menu-item-group>
          </div>
          </el-scrollbar>
          </div>
        </el-submenu>

        <el-submenu index="1-2">
          <template slot="title">
            <i class="el-icon-location video"></i>
            <span slot="title">影视作品</span>
          </template>
          <el-scrollbar style="height:500px" id="el-scrollbar__wrap">
          <div v-for="(value,key,index) in categoryAndWorks['影视']" :key="index">
            <el-menu-item-group>
              <span slot="title">{{key}}</span>
              <div  v-for="(item, index1) in value" :key="index1">
                <el-menu-item :index="item.id+'-'+item.name" >
                  <div>
                    <span>{{item.name}}</span>
                    <span style="color: #7dbfff">{{'('+item.count+')'}}</span>
                  </div>
                </el-menu-item>
              </div>
            </el-menu-item-group>
          </div>
          </el-scrollbar>
        </el-submenu>
      </el-menu>
    </div>
    <div style="width:83%;float:right;margin-top:0px;height:100%" ref="parent">
<!--      <keep-alive include="WorldMap">-->
<!--        <router-view></router-view>-->
<!--      </keep-alive>-->
<!--        <h2>{{parentHeight}}</h2>-->
<!--        <h2>{{parentWidth}}</h2>-->
      <div v-if="isShow" style="width: 100%">
        <WorldMap :workID="Number(workId)" :workName="workName"/>
      </div>
      <router-view :key="$route.fullPath"></router-view>
    </div>
  </div>
</template>

<script>
import {getPolarityCountries} from "../../api/polarityAPI";
import {getAllSubcategory} from "../../api/monitor_workAPI";
import WorldMap from "../../components/user/common/WorldMap.vue";
import {getCommentNumByWorkIdAndCountry} from "../../api/commentAPI";
import {getWorkAndCommentNumByCategory} from "../../api/monitor_workAPI";

export default {
  name: "EffectPage",
  components: {
      WorldMap
  },
  data() {
    return {
      isShow:true,
      isCollapse: false,
      workId:0,
      country:null,
      categoryAndWorks: {
        "文学": '',
        "影视": ''
      },
      workName:"整体",
      params:{},
      countries: [
        // '全球',
        /*'美国',
        '英国',
        '法国',
        '俄罗斯',
        '德国',
        '加拿大',
        '日本',
        '印度',
        '意大利',
        '巴西',
        '澳大利亚',
        '韩国',
        '墨西哥',
        '瑞士',
        '波兰',
        '泰国',
        '新加坡',
        '越南',
        '马来西亚',
        '南非',
        '巴基斯坦',
        '哥伦比亚',*/
      ]
    }
  },
  updated() {
      // console.log(this.$refs.parent.clientWidth,"width")
  },
    mounted() {
      // console.log(document.getElementById("app"))

    this.$bus.$on("pushSentimentAssessment",(data,workName,commentNum)=>{
      // console.log(data,workName)
      if(data && commentNum !==0 && commentNum !=='0'){
          this.isShow=false
          this.country=data
          // this.$router.push({
          //     name:"传播效果评估展示",
          //     // params:{
          //     //   workId:this.workId,
          //     //   country:data,
          //     // },
          //     query:{
          //         workId:this.workId,
          //         country:data,
          //         workName:workName
          //     }
          // })
          this.$router.push({
            name:"传播效果评估展示1",
            // params:{
            //   workId:this.workId,
            //   country:data,
            // },
            query:{
              workId:this.workId,
              country:data,
              workName:workName
            }
          })
      } else{
          // alert("该国家暂时没有数据！")
          this.$message({
              showClose: true,
              message: '该国家暂时没有数据！',
              type: 'warning'
          });
      }
      // this.isShow=false
      // this.country=data
      // this.$router.push({
      //   name:"传播效果评估展示",
      //   // params:{
      //   //   workId:this.workId,
      //   //   country:data,
      //   // },
      //   query:{
      //       workId:this.workId,
      //       country:data,
      //       workName:workName
      //   }
      // })
    })
   this.$bus.$on("mapShow",(data)=>{
       this.isShow=true
       // window.resize()
   })
   this.$bus.$on("mapNotShow",(data)=>{
     this.isShow=false
   })
   //该方法用于解决在查看某一个国家总体情感分布时，若是用户点击刷新，则本组件内country刷新导致侧边栏点击问题
    this.$bus.$on("getCountry",(data)=>{
      this.country=data
    })
    this.$bus.$on("noData",(data,country)=>{
      this.$router.push({
        name:"无极性情感分布数据",
        query:{
          workName:data,
          country:country
        }
      })
    })
  },
  beforeDestroy(){
    this.$bus.$off('pushSentimentAssessment')
    this.$bus.$off('mapShow')
    this.$bus.$off('mapNotShow')
    this.$bus.$off('getCountry')
    this.$bus.$off('noData')
  },
  computed: {
    workId2() {
      if (this.$route.query.workId !== null) {
        return this.$route.query.workId
      } else {
        return this.workId
      }
    }
  },
  async created() {
    // console.log(document.getElementById("app"))
    // if (this.$route.query.workId !== null) {
    //   this.workId = this.$route.query.workId
    // }
    await getPolarityCountries().then((res)=>{
      if (res.code === "0") {
        this.countries = ["全球"]
        this.countries = this.countries.concat(res.data)
      }
    })
    // getAllSubcategory().then((res)=>{
    //     // console.log('ok')
    //     if(res.code==='0'){
    //         // console.log(res.data,"data")
    //
    //         this.categoryAndWorks=res.data
    //     }
    // })
    getWorkAndCommentNumByCategory().then((res)=>{
      if(res.code==='0' || res.code===0){
        this.categoryAndWorks=res.data
      }
    })
    // console.log(this.countries)
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleSelect(idAndWork){
      let id = idAndWork.split('-')[0]
      let workName = idAndWork.split('-')[1]
      this.workId=id
      this.workName=workName
      // console.log(this.isShow,"isShow")
      if(!this.isShow){
        // console.log("切换")
        //   console.log(this.$route,"route")
        this.params.workId=this.workId
        this.params.country=this.country
        getCommentNumByWorkIdAndCountry(this.params).then((res)=>{
          if(res.code==='0' || res.code===0){
            let count=res.data
            if(count===0 || count ==='0'){
              this.$bus.$emit('noData',this.workName,this.country)
            }else{
              this.$bus.$emit('pushSentimentAssessment',this.country,this.workName)
            }
          }else{
            alert("加载出错！")
          }
        })
      }
      // console.log(this.workId,222)
      // this.$router.push({
      //     name:'世界地图',
      //     params:{
      //         workId:id
      //     }
      // })
    }
  }
}
</script>

<style scoped>
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.box-card {
  /*background-color: #dde5f3;*/
  background: url("../../assets/img/card_bg3.jpg") center center;
  margin-top: 20px;
}

#app {
  /*background-color: #ecf5ff;*/
  /*background: transparent;*/
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  height:100%;
  margin-bottom: 50%;
  margin-right: 1%;
  background-color: red;
}

.item-card {
  background-color: #f4f7fa;
  cursor: pointer;
  height: 100px;
  font-size: 20px;
  text-align: center;
  line-height: 60px;
}

.item-card:hover {
  background-color: #3976b8;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 255px;
  min-height: 400px;
}
.book{
  content: url('../../assets/img/book.svg');
}
.video{
  content: url('../../assets/img/video.svg');
}
.el-menu >>> .el-menu-item{
  font-size:22px
}
span{
  font-size: 20px;
}
.el-radio-button >>> .el-radio-button__inner{
  font-size: 18px;
}
</style>
<style scoped lang="css">
/* 解决自定义滚动条 x 轴显示问题 */
/deep/ .el-scrollbar__wrap
{
  overflow-x: hidden;
}
</style>
