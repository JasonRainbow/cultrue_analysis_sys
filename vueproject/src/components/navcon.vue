/**
* 头部菜单
*/
<template>
  <el-menu class="el-menu-demo" mode="horizontal" router background-color="#334157" text-color="#fff" active-text-color="#fff">
    <div class="buttonimg">
      <img class="showimg" :src="collapsed?imgsq:imgshow" @click="toggle(collapsed)">
    </div>
    <screen-full id="screenfull" class="right-menu-item hover-effect" />
    <div class="avatar-wrapper">
      <el-avatar size="medium" :src="avatar" class="user-avatar"></el-avatar>
<!--      <img :src="avatar" class="user-avatar">-->
    </div>
    <el-submenu index="2" style="float: right">
      <!-- <template slot="title">{{user.userRealName}}</template> -->
      <template slot="title">{{ nickname }}</template>
<!--      <el-menu-item index="2-1">设置</el-menu-item>-->
      <el-menu-item index="/admin/profile">
        个人中心
      </el-menu-item>
      <el-menu-item @click="exit()">退出</el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<script>

import ScreenFull from './ScreenFull'
export default {
  name: 'navcon',
  components: { // 声明组件
    ScreenFull
  },
  data() {
    return {
      collapsed: true,
      imgshow: require('../assets/img/show.png'),
      imgsq: require('../assets/img/sq.png'),
    }
  },
  // 创建完毕状态(里面是操作)
  created() {

  },
  computed: {
    avatar() {
      return this.$store.getters.avatar
    },
    nickname() {
      return this.$store.getters.nickname
    }
  },
  methods: {
    // 退出登录
    exit() {
      this.$confirm('退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          setTimeout(() => { // 管理员退出登录
            this.$store.dispatch("Logout").then(()=>{
              this.$message({
                type: 'success',
                message: '已退出登录!'
              })
              this.$router.push("/admin/login") // 跳转到登录页面
            })
          }, 1000)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 切换显示
    toggle(showtype) {
      this.collapsed = !showtype
      this.$bus.$emit('toggle', this.collapsed)
    }
  }
}
</script>
<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
}

.buttonimg {
  //height: 60px;
  background-color: transparent;
  border: none;
  display: inline-block;
  cursor: pointer;
}
.showimg {
  width: 26px;
  height: 26px;
  position: absolute;
  top: 17px;
  left: 17px;
}
.showimg:active {
  border: none;
}

.user-avatar {
  width: 40px;
  height: 40px;
  position: absolute;
  top: 10px;
  right: 140px;
  /*border-radius: 10px;*/
}

.avatar-wrapper {
  display: inline;
}

.right-menu-item {
  display: inline-block;
  padding: 0 8px;
  height: 100%;
  font-size: 18px;
  color: #5a5e66;
  vertical-align: center;
  position: absolute;
  right: 190px;
  top: 20px;

  &.hover-effect {
    cursor: pointer;
    transition: background .3s;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }
}



</style>
