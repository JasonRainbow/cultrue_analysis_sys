
<template>
  <div id="layout-header" :class="{'fixed':fixed,'hidden':hidden,'e':e && index==0}"
       @click.stop="mobileShow=false">
    <div class="site-logo">
      <router-link to="/">
        <p>W.A.Y</p>
      </router-link>
    </div>
    <!-- 这个是右上角斜边矩形的实现代码 -->
    <template v-if="index == 0 && e">
      <div class="top-bg"></div>
      <div class="top-bg1"></div>
      <div class="top-bg2"></div>
    </template>
    <div class="menus-btn" @click.stop="mobileShow=!mobileShow">
      Menus
    </div>
    <div class="site-menus" :class="{'mobileShow':mobileShow}" @click.stop="mobileShow=!mobileShow">
      <div class="menu-item" v-for="menu in menus" :class="[{'active':index == menu.id},{'hasChild':menu.id==1}]"
           @click="change(menu.id)">
        <a class="aa">{{menu.name}}
        </a>
      </div>
      <div class="menu-item" @click="change(4)">
        <a class="aa">登录</a>
      </div>
      <div class="menu-item"><a @click="drawer = true"><i class="el-icon-s-fold"></i></a></div>
    </div>
    <el-drawer
      :visible.sync="drawer"
      direction="rtl"
      :modal="false">
      <p class="title"></p>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "HeaderBar",
  data() {
    return {
      lastScrollTop: 0,
      fixed: false,
      hidden: false,
      category: [],
      mobileShow: false,
      index: 0,
      e: false,
      menus: [{
        name: "首页",
        link: "/",
        id: 0
      }, {
        name: "栏目",
        link: "/articles",
        id: 1
      }, {
        name: "友链",
        link: "/friend",
        id: 2
      }, {
        name: "关于",
        link: "/about",
        id: 3
      }],
      drawer: true
    }
  },
  mounted() {
    window.addEventListener('scroll', this.watchScroll)
    this.fetchCategory()
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.watchScroll)
  },
  methods: {
    watchScroll() {
      let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      if (scrollTop === 0) {
        this.fixed = false;
        this.e = true;
      } else if (scrollTop >= this.lastScrollTop) {
        this.fixed = false;
        this.hidden = true;
        this.e = false;
      } else {
        this.fixed = true
        this.hidden = false
        this.e = false;
      }
      this.lastScrollTop = scrollTop
    },
    change(id) {
      this.index = id;
    }
  },
  created() {
    this.$bus.on("setIdx", (val) => {
      this.index = val;
      if (this.index == 0) {
        this.e = true;
      }
    })
  }
}
</script>
<style lang="less">
#layout-header .el-image {
  width: 50px;
  border-radius: 50%;

  .image-slot {
    width: 50px;
    height: 50px;
    border-radius: 50%;
  }
  .el-drawer__wrapper{
    .el-drawer{
      -webkit-transform: skew(30deg);
      -moz-transform: skew(30deg);
      -o-transform: skew(30deg);
      transform: skew(30deg);
    }
  }
}
</style>
<style scoped lang="less">
.top-bg {
  width: 100%;
  background: linear-gradient(to left, rgba(116, 84, 169, 0.9) 0%, rgba(49, 126, 206, 0.9) 100%);
  height: 3px;
  position: absolute;
  top: 0;
  left: 0;
}

.top-bg1 {
  position: absolute;
  right: -50px;
  top: 0;
  width: 220px;
  height: 100%;
  background-color: #2EA7E0;
  -webkit-transform: skew(30deg);
  -moz-transform: skew(30deg);
  -o-transform: skew(30deg);
  transform: skew(30deg);
  background: linear-gradient(to bottom, rgba(116, 84, 169, 0.9) 0%, rgba(49, 126, 206, 0.9) 100%);
}


.e {
  background-color: rgba(0, 0, 0, 0.1) !important;
}

a {
  cursor: pointer;
}

.active .aa {
  color: #2ea7e0 !important;
}

.active::before {
  height: 2px;
  position: absolute;
  bottom: -14px;
  background-color: #2ea7e0;
  content: "";
  transition: all .6s;
  z-index: -1;
  width: 100%;
  left: 0;
}

.user-avatar-image {
  cursor: pointer;
}


#layout-header {
  background-color: #009688;
  position: fixed;
  top: 0;
  z-index: 9;
  width: 100%;
  height: 80px;
  padding: 0 80px;
  padding-right: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: .3s all ease;
  -webkit-transition: .3s all ease;
  -moz-transition: .3s all linear;
  -o-transition: .3s all ease;
  -ms-transition: .3s all ease;

  &.hidden {
    top: -100px;
  }

  &.fixed {
    background-color: #009688;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
}

.site-logo {
  text-align: center;
  color: #FFFFFF;
  img {
    width: 60px;
    height: 60px;
  }

  p.site-name {
    font-size: 15px;
    font-weight: bold;
    position: relative;
    top: -10px;
  }
}

.menus-btn {
  display: none;
  visibility: hidden;
}

.site-menus {
  display: flex;
  align-items: center;

  .menu-item {
    min-width: 60px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    position: relative;
    color: #FFFFFF;

    a {
      padding: 12px 10px;
      color: #545454;
      font-weight: bold;
      font-size: 16px;
      color: #FFFFFF;

      &:hover {
        color: #2ea7e0;
      }
    }

    &:not(:last-child) {
      margin-right: 15px;
    }

    &.hasChild:hover .childMenu {
      opacity: 1;
      visibility: visible;
      transform: translateY(-5px);
    }
  }

  .childMenu {
    width: 130px;
    background-color: #FDFDFD;
    border-radius: 3px;
    border: 1px solid #ddd;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    position: absolute;
    top: 55px;
    z-index: 2;
    opacity: 0;
    visibility: hidden;
    transition: .7s all ease;
    -webkit-transition: .6s all ease;
    -moz-transition: .6s all linear;
    -o-transition: .6s all ease;
    -ms-transition: .6s all ease;

    &:before,
    &:after {
      content: '';
      position: absolute;
      width: 0;
      height: 0;
      border-left: 6px solid transparent;
      border-right: 6px solid transparent;
      border-bottom: 8px solid white;
      top: -8px;
      left: 20px;
    }

    &:before {
      top: -9px;
      border-bottom: 8px solid #ddd;
    }

    .sub-menu {
      height: 40px;
      line-height: 40px;
      position: relative;
      color: #000000;

      &:not(:last-child):after {
        /*position: absolute;*/
        content: '';
        width: 50%;
        height: 1px;
        color: #ff6d6d;
        bottom: 0;
        left: 25%;
        z-index: 99;
      }

      a {
        color: #545454;
        font-weight: normal;
      }

      a:hover {
        color: #2EA7E0;
      }
      &:hover{
        background-color: #F5F5F5;
      }
    }
  }
}

@media (max-width: 960px) {
  #layout-header {
    padding: 0 20px;
  }
}

@media (max-width: 600px) {
  #layout-header {
    padding: 0 10px;
  }

  .menus-btn {
    display: block;
    visibility: visible;
    color: #FFFFFF;
  }

  .site-menus {
    position: absolute;
    display: none;
    visibility: hidden;
    background-color: #F9F9F9;
    width: 100%;
    left: 0;
    top: 80px;
    z-index: -9;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .menu-item {
      position: relative;
      height: unset;

      &:not(:last-child) {
        margin-right: 0;
      }
    }

    .header-search {
      color: #000000 !important;
    }

    .site-menus .menu-item a {
      color: #000000;
    }

    .childMenu {
      position: relative;
      width: 100%;
      top: 0;
      background-color: #F3F3F3;
      opacity: 1;
      visibility: visible;
      border: none;
      box-shadow: none;

      &:before,
      &:after {
        content: '';
        position: relative;
        width: 0;
        height: 0;
        border-left: 0;
        border-right: 0;
        border-bottom: 0;
      }
    }
  }

  .site-menus.mobileShow {
    display: inline-block;
    visibility: visible;
    z-index: 99;
  }
}
</style>
