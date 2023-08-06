<template>
  <el-container class="user-index">
    <el-header class="no-padding" style="height: 150px">
      <HeaderBar2 :active-index="activeIndex" :login-flag="loginFlag" :key="activeIndex"></HeaderBar2>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer class="no-padding" style="margin-top: 20px"><MyFooter></MyFooter></el-footer>
  </el-container>
</template>

<script>
import MyFooter from "../components/user/MyFooter";
import HeaderBar2 from "../components/user/HeaderBar2";
export default {
  name: "UserIndex",
  components: { // 注册组件
    MyFooter,
    HeaderBar2
  },
  data() {
    return {
      routes_map: {
        "/home": 0,
        "/effect": 1,
        "/background": 3,
        "/team": 4,
        "/question": 5,
        "/personal": 6,
        "/not-login": 6,
        "/sentiment-assessment":1
      },
      activeIndex: 0,
      loginFlag: true,
    }
  },
  methods: {
    judgeLogin(){ //判断用户是否登录
      this.loginFlag = Boolean(localStorage.getItem("user"));
    }
  },
  updated() {
    // console.log("1111")
    // console.log(this.$route.path)
    this.judgeLogin()
    this.activeIndex = this.routes_map[this.$route.path]
  },
  created() {
    // console.log(this.$route.path)
    this.judgeLogin()
    this.activeIndex = this.routes_map[this.$route.path]
  }
}
</script>

<style scoped>
.user-index,el-main{
  background-color: #ecf5ff;
}

</style>
