<template>
  <div class="box-container">
    <div class="box-content">
      <div class="back-btn" @click="goBack">返回>></div>
      <EffectPredictLineChart :work-id="workId"></EffectPredictLineChart>
    </div>
  </div>
</template>

<script>
import EffectPredictLineChart from "../../components/user/charts/EffectPredictLineChart";

export default {
  name: "WorkEffectPredict",
  components: {
    EffectPredictLineChart,
  },
  data() {
    return {
      workId: Number(this.$route.query.workId)
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    }
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
