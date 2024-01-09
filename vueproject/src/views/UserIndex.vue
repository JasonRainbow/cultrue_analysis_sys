<template>
  <vuescroll>
    <el-container class="user-index">
      <el-header class="no-padding" style="height: 150px">
        <HeaderBar2></HeaderBar2>
      </el-header>
      <el-main>
        <keep-alive :include="[]">
          <router-view></router-view>
        </keep-alive>
      </el-main>
      <el-footer class="no-padding" style="margin-top: 60px"><MyFooter></MyFooter></el-footer>
    </el-container>
  </vuescroll>
</template>

<script>
import MyFooter from "../components/user/MyFooter";
import HeaderBar2 from "../components/user/HeaderBar2";
import vuescroll from "vuescroll";

export default {
  name: "UserIndex",
  components: { // 注册组件
    MyFooter,
    HeaderBar2,
    vuescroll
  },
  data() {
    return {

    }
  },
  methods: {
    // judgeLogin(){ //判断用户是否登录
    //   this.loginFlag = Boolean(localStorage.getItem("user"));
    // }
  },
  updated() {
    // console.log("1111")
    // console.log(this.$route.path)
    // this.judgeLogin()
    // this.activeIndex = this.routes_map[this.$route.path]
  },
  created() {
    // console.log(this.$route.path)
    // this.judgeLogin()
    // this.activeIndex = this.routes_map[this.$route.path]
  }
}
</script>

<style scoped>
.user-index,el-main{
  /*background-color: #ecf5ff;*/
  background: url("../assets/img/main_bg.jpg") center center;
}

</style>
