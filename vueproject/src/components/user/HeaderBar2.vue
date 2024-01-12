<template>
  <div id="main">
    <div>
      <div class="dashboard" @click="gotoDashboard">Dashboard</div>
      <div id="header-title">
        中国语言文化作品国际传播效果智能评测系统<br>
      </div>
      <div style="margin-bottom: 5px;font-family: Times New Roman, sans-serif; font-size: 20px; color: white; text-align: center">
        Intelligent Evaluation System for the International Communication Effect of Chinese Language and Culture Works
      </div>
    </div>

    <div id="show-login-div">
      <div id="user-info" v-if="loginFlag"> <!--显示登录用户-->
        <el-avatar :size="45" icon="el-icon-user-solid" :src="avatar" id="inner-user-avatar"></el-avatar>
        <div id="inner-user-nick">{{ nickname }}</div>
      </div>
      <div v-else>  <!--未登录可前往登录-->
        <router-link to="/login" style="color: #6dcdcd; font-weight: bold">前往登录>></router-link>
      </div>
    </div>
    <div id="nav-tab" ref="navtab">
      <div class="nav-tab-item active" v-for="(item,index) in items"
           :key="index"
           @click="switchMenu(index)"
           :style="{'width': `${activeIndex === index ? itemWidth + 10: itemWidth}%`}">
        <router-link :to="item.path" style="text-align: center; width: 100%;height: 100%;padding-top: 12px">
          <i :class='item.icon'></i>
          <p class="nav-tab-item_label" style="margin-top: 4px">{{item.label}}</p>
          <p class="nav-tab-item_label label-pro" style="margin-top: 2px">{{item.englishLabel}}</p>
        </router-link>
      </div>
      <div class="nav-tab-overlay" :style="{'left':`${activeIndex * itemWidth}%`,'width':`${itemWidth + 10}%`}"></div>
    </div>
  </div>
</template>

<script>
import {hasLogin} from "../../utils/auth";

export default {
  name: "HeaderBar2",
  props: {
    /*activeIndex: {
      type: Number,
      require: true
    },
    loginFlag: {
      type: Boolean,
      require: true
    }*/
  },
  data() {
    return {
      items: [
        { icon: 'nav-tab-item_icon iconfont icon-home', label: '首页', englishLabel: 'Home', path: '/home'},
        { icon: 'nav-tab-item_icon iconfont icon-send-fill', label: '传播效果分析', englishLabel: 'Effect Analysis', path: '/effect'},
        { icon: 'nav-tab-item_icon iconfont icon-zuopinzhanshi', label: '湖湘文化作品', englishLabel: 'Huxiang Works', path: '/special-column'},
        { icon: 'nav-tab-item_icon iconfont icon-shujuxianshi', label: '大屏模式', englishLabel: 'BigScreen', path: '/big-screen'},
        { icon: 'nav-tab-item_icon iconfont icon-zhinengtuijian', label: '作品推荐', englishLabel: 'Recommend', path: '/recommend'},
        { icon: 'nav-tab-item_icon iconfont icon-project_info', label: '背景介绍', englishLabel: 'Background', path: '/background'},
        { icon: 'nav-tab-item_icon iconfont icon-rencaishuangxuanhui', label: '团队介绍', englishLabel: 'About Us', path: '/team'},
        { icon: 'nav-tab-item_icon iconfont icon-a-commandinquiry-fill', label: '常见问题', englishLabel: 'FAQ', path: '/question'},
        { icon: 'nav-tab-item_icon iconfont icon-user', label: '个人中心', englishLabel: 'Profile', path: '/personal'},
      ],
      // activeIndex: Number(sessionStorage.getItem("activeIndex")),
      routes_map: {
        "/home": 0,
        "/effect": 1,
        "/recommend": 4,
        "/background": 5,
        "/team": 6,
        "/question": 7,
        "/personal": 8,
        "/not-login": 8,
        "/sentiment-assessment":1,
        "/special-column": 2,
        // "/effect/sentiment-assessment": 1,
        "/effect/sentimentAssessment":1,
        "/effect/no-data":1
      },
      activeIndex: 0,
      loginFlag: true,
    }
  },
  methods: {
    switchMenu(index) {
      this.activeIndex = index
      // sessionStorage.setItem("activeIndex", index)
    },
    judgeLogin(){ //判断用户是否登录
      this.loginFlag = hasLogin()
    },
    gotoDashboard() {
      if (hasLogin()) {
        // console.log("用户已登录")
        this.$router.push({
          path: "/dashboard",
          query: {

          }
        })
      } else {
        // console.log("用户未登录")
        this.$message.warning("请登录后查看")
      }
    }
  },
  computed: {
    itemWidth() {
      return 90 / this.items.length;
    },
    avatar() {
      return this.$store.getters.avatar
    },
    nickname() {
      return this.$store.getters.nickname
    }
  },
  created() {
    this.judgeLogin()
    this.activeIndex = this.routes_map[this.$route.path]
  },
  mounted() {
    // alert(this.$store.state.user)
    // alert("hello")
    /*let menuId = sessionStorage.getItem("activeIndex")
    alert(menuId)
    if (!menuId) menuId = 0
    if (menuId) {
      this.activeIndex = Number(menuId)
    }*/
  },
  watch: {
    // 监听路由的变化
    $route(to, from) {
      // console.log("路由变化了")
      // console.log("当前页面路由：", to);
      // console.log("上一个路由:", from)
      this.judgeLogin()
      this.activeIndex = this.routes_map[to.path]
    }
  }
}
</script>

<style scoped>

#main {
  background: url("../../assets/img/header_bg2.jpg") center center;
  border-radius: 0 0 90px 90px
}

/* 配色方案:
背景:7dbfff,
图标&标签:4298e7,
遮罩:e4f2ff */
* {
  margin: 0;
  padding: 0;
}

body {
  height: 100vh;
  background-color: #7dbfff;

  /* 居中 */
  display: flex;
  justify-content: center;
  align-items: center;
}

#nav-tab {
  position: relative;

  width: 100%;
  height: 100px;
  /*background-color: #195161;*/
  display: flex;

  border-bottom-left-radius: 90px;
  border-bottom-right-radius: 90px;

  overflow: hidden;

}

.nav-tab-item {
  height: 100%;

  z-index: 2;
  transition: 0.1s;
  cursor: pointer;

  /* 居中 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.nav-tab-item.active {
  width: 210px;
}

.nav-tab-item:hover {
  background-color: #9e2b2d;
  border-radius: 20px;
}

.nav-tab-item_icon {
  font-size: 26px;
  color: #ffffff;
  //transition: 0.3s;
  transform: translate(0, 0px);
}

.active .nav-tab-item_icon {
  transform: translate(0, -10px);
}

.nav-tab-item_label {
  font-size: 18px;
  //font-weight: 600;
  font-weight: bold;
  color: rgba(255,255,255,0.91);
  opacity: 0;
  //transition: 0.3s;
  user-select: none;
}

.active .nav-tab-item_label {
  opacity: 1;
}

.nav-tab-overlay {
  position: absolute;
  left: 0;
  top: 0;

  height: 100%;
  width: 210px;

  background-color: #9e2b2d;
  border-radius: 20px;

  //transition: 0.3s;
}

#header-title {
  //height: 50px;
  text-align: center;
  padding-top: 10px;
  /*color: #C03639;
  font-size: 35px;
  font-family: 华文楷体;*/
  /*text-shadow: 1px 1px #ac0d2a;*/
  //font-family: '华光淡古印_CNKI';
  font-family: 华文琥珀;
  font-size:41px;
  background-image: -webkit-linear-gradient(left, #ff0000, #e0c723 10%, #ff6a00 20%, #04dce3 30%, #CCCCFF 40%, #00FFFF 50%, #CCCCFF 60%, #CC00CC 70%, #CC00FF 80%, #66FFFF 90%, #ffff00 100%);
  -webkit-text-fill-color: transparent;
  /* 将字体设置成透明色 */
  -webkit-background-clip: text;
  /* 裁剪背景图，使文字作为裁剪区域向外裁剪 */
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
}

#show-login-div{
  height: 100px;
  width:  200px;
  /*background-color: #195161;*/
  position: absolute;
  top: 25px;
  right: 40px;
}
/*#inner-user-avatar{
  height: 50px;
  width:  50px;
  float: left;
}*/
#inner-user-nick{
  height: 20px;
  width: 140px;
  font-family: 华光中圆_CNKI;
  margin-top: 12px;
  margin-left: 10px;
  font-size: 18px;
  color: #E6A23C;
  float: right;
}

.label-pro {
  font-family: "Times New Roman", sans-serif;
  font-size: 19px;
}

@keyframes flash_out
{
  0% {
    transform: scale(0.9);
  }

  70% {
    transform: scale(1);
    box-shadow: 0 0 0 50px rgba(90, 153, 212, 0);
  }

  100% {
    transform: scale(0.9);
    box-shadow: 0 0 0 0 rgba(90, 153, 212, 0);
  }

}

.dashboard {
  position: absolute;
  height: 60px;
  width: 150px;
  line-height: 60px;
  text-align: center;
  left: 30px;
  top: 10px;
  font-size: 24px;
  font-family: "Cooper Black";
  border-radius: 50%;
  cursor: pointer;
  border: none;
  letter-spacing: -1px;
  color: #fff;
  background: #ce282a;
  box-shadow: 0 0 0 0 rgba(206, 40, 42, 0.5);
  -webkit-animation: flash_out 1.5s infinite;
}

</style>
