<template>
  <el-container class="user-index">
    <el-header class="no-padding" style="height: 150px">
      <HeaderBar2></HeaderBar2>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer class="no-padding" style="margin-top: 20px"><MyFooter></MyFooter></el-footer>
  </el-container>
</template>

<script>
import HeaderBar from "../components/user/HeaderBar";
import MyFooter from "../components/user/MyFooter";
import HeaderBar2 from "../components/user/HeaderBar2";
export default {
  name: "UserIndex",
  components: { // 注册组件
    HeaderBar,
    MyFooter,
    HeaderBar2
  }
}
</script>

<style scoped>
.user-index,el-main{
  background-color: #ecf5ff;
}

</style>
