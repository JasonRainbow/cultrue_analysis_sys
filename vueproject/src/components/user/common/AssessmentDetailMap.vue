<template>
  <div>
    <el-row align="center" justify="center" style="display: flex; justify-content: center;">
      <el-col :span="14" :xs="24" :md="23" align="center">
        <el-card class="box-card grid-content" shadow="always">
          <div style="text-align: left">
            <router-link to="/effect">
              <el-link type="warning" style="font-size: 18px;" @click="showMap">&lt;&lt;返回</el-link>
            </router-link>
            <el-button type="warning" style="float: right;color: #222;font-weight: bold" @click="changeStatus">{{'切换'+(isShow?'近一年极性情感变化趋势图':'极性情感占比图')}}</el-button>
          </div>
          <h2 style="text-align: center;margin-top: 20px" >{{title}}</h2>
          <div v-show="isShow">
<!--            <h2 style="text-align: center" >{{title}}</h2>-->
            <PolarityAnalysisPie :workId="workId" :country="country"></PolarityAnalysisPie>
          </div>
          <div v-show="!isShow">
            <PolarityLineChart :workId="Number(workId)" :country="country"></PolarityLineChart>
<!--            {{'查看'+isShow?'极性情感趋势折线图':'极性情感分布折线图'}}-->
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import PolarityAnalysisPie from "./PolarityAnalysisPie.vue";
import PolarityLineChart from "./PolarityLineChart.vue";
export default {
  name:"AssessmentDetailMap",
  components: {
    PolarityAnalysisPie,
    PolarityLineChart
  },
  data(){
    return {
      isShow:true,
      workId:0,
      workName:null,
      country:null,
      title:null
    }
  },
  methods:{
    showMap(){
      this.$bus.$emit('mapShow')
    },
    changeStatus(){
      this.isShow=!this.isShow
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.$bus.$emit('mapNotShow')
    })
  },
  beforeMount() {
    this.workId=this.$route.query.workId
    this.workName=this.$route.query.workName
    this.country=this.$route.query.country
    this.$bus.$emit('getCountry',this.country)
    if(this.workId==='0' || this.workId===0){
      this.title=this.country+' '+"整体极性情感分布"
    }else{
      this.title=this.workName+' '+this.country+' '+"极性情感分布"
    }
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
