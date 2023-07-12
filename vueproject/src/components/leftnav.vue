/**
* 左边菜单
*/
<template>
  <el-menu default-active="2" :collapse="collapsed" collapse-transition router
           :default-active="$route.path" unique-opened class="el-menu-vertical-demo"
           background-color="#334157" text-color="#fff" active-text-color="#ffd04b">
    <div class="logobox">
      <img class="logoimg" src="../assets/img/logo.jpg" alt="">
      <div style="color: white;vertical-align: top;margin-top: 5px; height: 40px;font-size: 18px;display: inline-block;">后台管理系统</div>
    </div>
    <el-menu-item index="/admin/home" key="0">
      <i class="el-icon-s-home"></i>
      <span>首页</span>
    </el-menu-item>
    <el-submenu v-for="menu in allmenu" :key="menu.menuid" :index="menu.menuname">
      <template slot="title">
        <i :class="menu.icon"></i>
        <span>{{menu.menuname}}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item v-for="chmenu in menu.menus" :index="'/'+chmenu.url" :key="chmenu.menuid">
          <i :class="chmenu.icon"></i>
          <span>{{chmenu.menuname}}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>
<script>
export default {
  name: 'leftnav',
  data() {
    return {
      collapsed: false,
      allmenu: []
    }
  },
  // 创建完毕状态(里面是操作)
  created() {
    // 获取图形验证码
    let res = {
      success: true,
      data: [
        {
          menuid: 1,
          icon: 'el-icon-s-promotion',
          menuname: '系统管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 2,
              icon: 'el-icon-s-custom',
              menuname: '用户管理',
              hasThird: 'N',
              url: 'admin/users',
              menus: null
            },
            {
              menuid: 3,
              icon: 'el-icon-user-solid',
              menuname: '管理员管理',
              hasThird: 'N',
              url: 'admin/admins',
              menus: null
            },
            {
              menuid: 4,
              icon: 'el-icon-collection',
              menuname: '热点文化作品管理',
              hasThird: 'N',
              url: 'admin/hotwork',
              menus: null
            },
          ]
        },
        {
          menuid: 22,
          icon: 'el-icon-monitor',
          menuname: '传播监测管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 23,
              icon: 'el-icon-files',
              menuname: '监测文化作品管理',
              hasThird: 'N',
              url: 'admin/monitor-work',
              menus: null
            },
            {
              menuid: 24,
              icon: 'el-icon-chat-dot-round',
              menuname: '作品评论管理',
              hasThird: 'N',
              url: 'admin/comments',
              menus: null
            },
            {
              menuid: 25,
              icon: 'el-icon-s-check',
              menuname: '监测申请管理',
              hasThird: 'N',
              url: 'admin/monitor-request',
              menus: null
            },
          ]
        },
        {
          menuid: 30,
          icon: 'el-icon-s-marketing',
          menuname: '传播分析管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 31,
              icon: 'el-icon-s-flag',
              menuname: '情感分析管理',
              hasThird: 'N',
              url: 'admin/sentiment',
              menus: null
            },
            {
              menuid: 32,
              icon: 'el-icon-coordinate',
              menuname: '情感极性分析管理',
              hasThird: 'N',
              url: 'admin/polarity',
              menus: null
            },
            {
              menuid: 33,
              icon: 'el-icon-coin',
              menuname: '词频统计管理',
              hasThird: 'N',
              url: 'admin/word-freq',
              menus: null
            },
          ]
        },
        {
          menuid: 150,
          icon: 'el-icon-pie-chart',
          menuname: '图表',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 159,
              icon: 'el-icon-data-analysis',
              menuname: '数据可视化',
              hasThird: 'N',
              url: 'charts/statistics',
              menus: null
            }
          ]
        }
      ],
      msg: 'success'
    }
          this.allmenu = res.data
    this.$root.Bus.$on('toggle', value => {
      this.collapsed = !value
    })
  }
}
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 220px;
  min-height: 400px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
  text-align: left;
}
.el-menu-item-group__title {
  padding: 0px;
}
.el-menu-bg {
  background-color: #1f2d3d !important;
}
.el-menu {
  border: none;
}
.logobox {
  height: 80px;
  line-height: 40px;
  color: #9d9d9d;
  font-size: 20px;
  text-align: center;
  padding: 20px 0px;
}
.logoimg {
  height: 40px;
}
</style>
