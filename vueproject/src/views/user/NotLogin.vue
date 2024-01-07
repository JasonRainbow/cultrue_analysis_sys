<template>
  <el-row style="display: flex; justify-content: center; margin-top: 25px">
    <el-col :span="16" :xs="24">
      <el-card class="box-card grid-content" style="height: 500px;">
        <div style="font-family: 华文新魏;font-size: 25px">您还没有登录，请先登录</div>
        <div style="margin-top: 100px">点击<router-link to="/login" style="color: #C03639; font-weight: bold">此处</router-link>跳转到登录界面</div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "NotLogin"
}
</script>

<style scoped>

.box-card {
  background: url("../../assets/img/card_bg2.jpg");
}

</style>
