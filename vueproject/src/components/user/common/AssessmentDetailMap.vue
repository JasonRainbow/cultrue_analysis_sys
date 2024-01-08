<template>
  <div>
    <el-row align="center" justify="center" style="display: flex; justify-content: center;">
      <el-col :span="14" :xs="24" :md="23" align="center">
        <el-card class="box-card grid-content" shadow="always">
          <div style="text-align: left">
            <router-link to="/effect">
              <el-link type="warning" style="font-size: 18px;" @click="showMap">&lt;&lt;返回</el-link>
            </router-link>
          </div>
          <h2 style="text-align: center" >{{title}}</h2>
          <div>
            <PolarityAnalysisPie :workId="workId"></PolarityAnalysisPie>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import PolarityAnalysisPie from "./PolarityAnalysisPie.vue";
export default {
  name:"AssessmentDetailMap",
  components: {
    PolarityAnalysisPie
  },
  data(){
    return {
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
