<template>
  <div class="box-container">
    <div class="box-content">
      <div class="back-btn" @click="goBack">返回>></div>
      <div style="font-size: 20px; color: #e2652f; margin: 10px auto 20px 20px; font-weight: bold">
        语言文化作品({{work.subCategory}})：<span style="color: #ef0000; ">{{work.name}} ({{work.englishName}})</span>
      </div>
      <EffectPredictLineChart :work-id="workId"></EffectPredictLineChart>
    </div>
  </div>
</template>

<script>
import EffectPredictLineChart from "../../components/user/charts/EffectPredictLineChart";
import {getMonitorWorkById} from "../../api/monitor_workAPI";

export default {
  name: "WorkEffectPredict",
  components: {
    EffectPredictLineChart,
  },
  data() {
    return {
      workId: Number(this.$route.query.workId),
      work: {
        id: 1,
        name: '边城',
        englishName: 'The Border Town',
        imgUrl: '',
        subCategory: '小说',
        attachInfo: ""
      },
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    getWorkData() {
      getMonitorWorkById(this.workId).then((res)=>{
        if (res.code === "0") {
          // console.log(res)
          this.work = res.data
          // console.log(this.work)
        }
      })
    },
  },
  created() {
    this.getWorkData()
  }
}
</script>

<style scoped>
.box-container {
  display: flex;
  justify-content: center;
  height: 100%;
  background:url('../../assets/img/card_bg2.jpg')
}
.box-content {
  border-radius: 30px;
  margin-top: 1.5%;
  background: url("../../assets/img/card_bg3.jpg") center center;
  height: 90%;
  width: 60%;
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
