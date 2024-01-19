<template>
  <div style="width: 100%;height: 100%">
    <el-row align="center" justify="center" style="display: flex; justify-content: center;">
      <el-col :span="14" :xs="24" :md="23" align="center">
        <el-card class="box-card grid-content" shadow="always">
          <div style="text-align: right">
            <el-button  :disabled="isDisable1" type="warning" style="color: #222;font-weight: bold;margin-right: 10px" @click="changeStatus1">{{'切换极性情感占比图'}}</el-button>
            <el-button  :disabled="isDisable2" type="warning" style="color: #222;font-weight: bold;margin-right: 10px" @click="changeStatus2">{{'切换近一年极性情感变化趋势图'}}</el-button>
            <el-button  :disabled="isDisable3" type="warning" style="color: #222;font-weight: bold;margin-right: 10px" @click="changeStatus3">{{'切换评论数量变化趋势图'}}</el-button>
            <el-button  :disabled="isDisable4" type="warning" style="color: #222;font-weight: bold;margin-right: 10%" @click="changeStatus4">{{'切换极性评论排名图'}}</el-button>
            <router-link to="/effect">
              <el-link type="warning" style="font-size: 18px;" @click="showMap">返回>></el-link>
            </router-link>
          </div>
          <h2 style="text-align: center;margin-top: 20px" >{{title}}</h2>
          <div v-show="isShow1">
            <PolarityAnalysisPie :workId="workId" :country="country"></PolarityAnalysisPie>
          </div>
          <div v-show="isShow2">
            <PolarityLineChart :workId="Number(workId)" :country="country"></PolarityLineChart>
          </div>
          <div v-show="isShow3">
            <NearCommentNumLineChart :workId="Number(workId)" :country="country"></NearCommentNumLineChart>
          </div>
          <div v-show="isShow4">
            <PolarityCommentList :workId="Number(workId)" :country="country"></PolarityCommentList>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import PolarityAnalysisPie from "./PolarityAnalysisPie.vue";
import PolarityLineChart from "./PolarityLineChart.vue";
import NearCommentNumLineChart from "./NearCommentNumLineChart.vue";
import PolarityCommentList from "./PolarityCommentList.vue";
export default {
  name:"AssessmentDetailMap",
  components: {
    PolarityAnalysisPie,
    PolarityLineChart,
    NearCommentNumLineChart,
    PolarityCommentList
  },
  data(){
    return {
      isShow1:true,
      isShow2:false,
      isShow3:false,
      isShow4:false,
      isDisable1:true,
      isDisable2:false,
      isDisable3:false,
      isDisable4:false,
      workId:0,
      workName:null,
      country:null,
      title:null
    }
  },
  methods:{
    showMap(){
      this.$store.commit('setMapStatus',true)
    },
    changeStatus1(){
      this.title=this.workName+' '+this.country+' '+"极性情感分布"
      this.isShow1=true
      this.isShow2=false
      this.isShow3=false
      this.isShow4=false
      this.isDisable1=true
      this.isDisable2=this.isDisable3=this.isDisable4=false
    },
    changeStatus2(){
      this.title=this.workName+' '+this.country+' '+"极性情感变化趋势"
      this.isShow1=false
      this.isShow2=true
      this.isShow3=false
      this.isShow4=false
      this.isDisable2=true
      this.isDisable1=this.isDisable3=this.isDisable4=false
    },
    changeStatus3(){
      this.title=this.workName+' '+this.country+' '+"评论数量变化趋势"
      this.isShow1=false
      this.isShow2=false
      this.isShow3=true
      this.isShow4=false
      this.isDisable3=true
      this.isDisable1=this.isDisable2=this.isDisable4=false
    },
    changeStatus4(){
      this.title=this.workName+' '+this.country+' '+"极性评论排名(节选点赞数量前10)"
      this.isShow1=false
      this.isShow2=false
      this.isShow3=false
      this.isShow4=true
      this.isDisable4=true
      this.isDisable1=this.isDisable2=this.isDisable3=false
    }
  },
  beforeRouteEnter (to, from, next) {
    // next(vm => {
    //   vm.$store.commit('setMapStatus',false)
    //   // vm.$bus.$emit('mapNotShow')
    // })
    next()
  },
  mounted() {
    console.log(this.$store.state.isShow,"isShow2")
  },
  beforeMount() {
    this.$store.commit('setMapStatus',false)
    this.workId=this.$route.query.workId
    this.workName=this.$route.query.workName
    this.country=this.$route.query.country
    this.$bus.$emit('getCountry',this.country)
    this.title=this.workName+' '+this.country+' '+"极性情感分布"
    // if(this.workId==='0' || this.workId===0){
    //   this.title=this.country+' '++"极性情感分布"
    // }else{
    //   this.title=this.workName+' '+this.country+' '+"极性情感分布"
    // }
  }
}
</script>
<style scoped>
.box-card {
  /*background-color: #dde5f3;*/
  background: url("../../../assets/img/card_bg3.jpg") center center;
  margin-top: 20px;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.box-card /deep/ .el-link--inner {
  color: #e25f2f;
  font-weight: bolder;
}
</style>
