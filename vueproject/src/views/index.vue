<template>
  <!-- <el-container class="index-con">
    <el-header class="index-header">
      <navcon></navcon>
    </el-header>
    <el-container class="index-con">
      <el-aside :class="showclass">
        <leftnav></leftnav>
      </el-aside>
      <el-container class="main-con">
        <el-main clss="index-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container> -->

  <el-container id="index-con">
    <el-aside :class="showclass">
      <leftnav></leftnav>
    </el-aside>
    <el-container id="main-con">
      <el-header id="index-header">
        <navcon></navcon>
      </el-header>
      <el-main id="index-main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
// 导入组件
import navcon from '../components/navcon.vue'
import leftnav from '../components/leftnav.vue'
export default {
  name: 'index',
  data() {
    return {
      showclass: 'asideshow',
      showtype: false
    }
  },
  // 注册组件
  components: {
    navcon,
    leftnav
  },
  methods: {},
  created() {
    // 监听
    this.$bus.$on('toggle', value => {
      if (value) {
        this.showclass = 'asideshow'
      } else {
        setTimeout(() => {
          this.showclass = 'aside'
        }, 300)
      }
    })
  },
  beforeUpdate() {},
  // 挂载前状态(里面是操作)
  beforeMount() {
    // 弹出登录成功
    /*this.$message({
      message: '登录成功',
      type: 'success'
    })*/
  },
  beforeDestroy() {
    this.$bus.$off('toggle')
  }
}
</script>
<style >
#index-con {
  height: 100%;
  width: 100%;
  box-sizing: border-box;
}

.aside {
  width: 64px !important;
  height: 100%;
  background-color: #334157;
  margin: 0px;
}
.asideshow {
  width: 220px !important;
  height: 100%;
  background-color: #334157;
  margin: 0px;
}
#index-header,
#index-main {
  padding: 0;
  border-left: 2px solid #333;
}

#index-main {
  padding-left: 7px;
  padding-top: 10px;
}
</style>
