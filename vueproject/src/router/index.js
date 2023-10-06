// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录

// 管理员首页
import index from '@/views/index';

// 图表界面
import statistics from '../views/charts/statistics.vue';

import Login2 from '../views/Login2.vue'
import userAdmin from "../views/admin/userAdmin";

// 后台管理页面组件
import AdminProfile from '../views/admin/profile/index'
import adminInfo from "../views/admin/adminInfo";
import AdminHome from "../views/admin/AdminHome";
import HotWorkAdmin from "../views/admin/HotWorkAdmin";
import MonitorWorkAdmin from "../views/admin/MonitorWorkAdmin";
import MonitorRequestAdmin from "../views/admin/MonitorRequestAdmin";
import CommentAdmin from "../views/admin/CommentAdmin";
import SentimentAdmin from "../views/admin/SentimentAdmin";
import PolarityAdmin from "../views/admin/PolarityAdmin";
import WordFreqAdmin from "../views/admin/WordFreqAdmin";

// 客户端界面
import UserIndex from "../views/UserIndex";
import BigScreen from "../components/user/BigScreen";
import HomePage from "../views/user/HomePage";
import UserLogin from "../views/user/UserLogin";
import UserRegister from "../views/user/UserRegister";
import PersonalCenter from "../views/user/PersonalCenter";
import EffectPage from "../views/user/EffectPage";
import AssessmentDetailChart from "../components/user/charts/AssessmentDetailChart";
import background from "../views/user/background";
import HotComment from "../components/user/common/HotComment";
import normalQuestion from "../components/user/common/normalQuestion";
import NotLogin from "../views/user/NotLogin";
import ShowWorldMap from "../views/user/ShowWorldMap";
import TeamIntroduction from "../components/user/common/TeamIntroduction";
import PersonalRecommendation from "../views/user/PersonalRecommendation.vue";
import store from "../vuex/store";

// 启用路由
Vue.use(Router);

// 导出路由
const router = new Router({
  mode: 'hash',
  routes: [
    {
      path: '/admin/login',
      name: '管理员登录',
      component: Login2,
      hidden: true,
      meta: {
        title: '管理员登录',
        requireAuth: false
      }
    }, {
      path: '/login',
      name: '用户登录',
      component: UserLogin,
      hidden: true,
      meta: {
        title: '用户登录',
        requireAuth: false
      }
    }, {
      path: '/register',
      name: '用户注册',
      component: UserRegister,
      hidden: true,
      meta: {
        title: '用户注册',
        requireAuth: false
      }
    }, {
      path: '/admin',
      name: '后台管理系统',
      component: index,
      iconCls: 'el-icon-tickets',
      redirect: '/admin/home',
      meta: {
        title: '后台管理首页',
        requireAuth: true,
      },
      children: [
        {
          path: '/admin/home',
          name: '后台管理首页',
          component: AdminHome,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/profile',
          name: '管理员个人中心',
          component: AdminProfile,
          meta: {
            requireAuth: true
          }
        }, {
          path: '/admin/users',
          name: '用户信息管理',
          component: userAdmin,
          meta: {
            requireAuth: true
          }
        }, {
          path: '/admin/admins',
          name: '管理员信息管理',
          component: adminInfo,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/hotwork',
          name: '热点文化作品管理',
          component: HotWorkAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/monitor-work',
          name: '监测文化作品管理',
          component: MonitorWorkAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/monitor-request',
          name: '监测请求管理',
          component: MonitorRequestAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/comments',
          name: '作品评论管理',
          component: CommentAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/sentiment',
          name: '情感分析管理',
          component: SentimentAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/polarity',
          name: '情感极性分析管理',
          component: PolarityAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/word-freq',
          name: '词频统计管理',
          component: WordFreqAdmin,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/charts/statistics',
          name: '数据可视化',
          component: statistics,
          meta: {
            requireAuth: true
          }
        }]
    },
    {
      path: "/big-screen",
      name: "大屏模式",
      component: BigScreen,
      meta: {
        requireAuth: false
      }
    },
    {
      path: "/worldMap",
      name: "情感世界分布",
      component: ShowWorldMap,
      meta: {
        requireAuth: false
      }
    },
    {
      path: "/",
      name: "客户端", // 客户端网页的首页
      component: UserIndex,
      meta: {
        requireAuth: false
      },
      redirect: "/home",
      children: [ // 子路由
        {
          path: "/home",
          name: "客户端首页",
          component: HomePage,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/effect",
          name: "传播效果评估",
          component: EffectPage,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/personal",
          name: "用户个人中心",
          component: PersonalCenter,
          meta: {
            requireAuth: true
          }
        },
        {
          path: "/sentiment-assessment",
          name: "传播效果评估展示",
          component: AssessmentDetailChart,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/background",
          name: "背景介绍",
          component: background,
          meta: {
            requireAuth: false
          }
        }, {
          path: "/team",
          name: "团队介绍",
          component: TeamIntroduction,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/question",
          name: "常见问题",
          component: normalQuestion,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/recommend",
          name: "个性化推荐",
          component: PersonalRecommendation,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/not-login",
          name: "未登录",
          component: NotLogin,
          meta: {
            requireAuth: false
          }
        },
      ]
    }
  ]
})

// 对路由进行权限控制
// 全局路由拦截器  前置路由守卫
router.beforeEach((to, from, next) => {
  if (to.matched.length !== 0) { // 路由能够匹配
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
      if (to.path === "/personal") { // 访问个人中心页面
        if (Boolean(localStorage.getItem("user"))) {
          let user = store.state.user
          if (!user) {
            // console.log("存储user到vuex")
            user = store.state.user = JSON.parse(localStorage.getItem("user"))
          }
          if (user.avatar == null || user.avatar === "") {
            store.state.user.avatar = require("../assets/img/avatar.jpeg")
          }
          next()
        } else {
          next({
            path: "/not-login",
            query: {redirect: to.fullPath}
          })
        }
      } else if (Boolean(localStorage.getItem("admin"))) { // 通过vuex state获取当前的user是否存在
        const admin = JSON.parse(localStorage.getItem("admin"));
        console.log(admin)
        if (admin.avatar == null || admin.avatar === "") {
          admin.avatar = require("../assets/img/avatar.jpeg")
        }
        store.state.admin = admin;
        next();
      } else {
        next({
          path: '/admin/login',
          query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
        })
      }

    } else {
      /*if (Boolean(localStorage.getItem("admin"))) { // 判断是否登录
          if (to.path != "/" && to.path != "/login") { //判断是否要跳到登录界面
              next();
          } else {
              /!**
               * 防刷新，如果登录，修改路由跳转到登录页面，修改路由为登录后的首页
               *!/
              next({
                  path: '/admin/home'
              })
          }
      } else {
          next();
      }*/
      next();

    }
  } else {
    next({
      path: '/',
      query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
    })
  }
})

// 全局后置路由守卫——初始化的时候被调用、每次路由切换后被调用
router.afterEach((to, from) => {
  document.title = to.name
})

export default router
