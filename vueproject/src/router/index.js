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
import login3 from "../views/login3";
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

// 启用路由
Vue.use(Router);

// 导出路由
export default new Router({
  routes: [
    {
    path: '/admin/login',
    name: '管理员登录',
    component: Login2,
    hidden: true,
    meta: {
      requireAuth: false
    }
  }, {
    path: '/login',
    name: '用户登录',
    component: UserLogin,
    hidden: true,
    meta: {
      requireAuth: false
    }
  },{
      path: '/register',
      name: '用户注册',
      component: UserRegister,
      hidden: true,
      meta: {
        requireAuth: false
      }
    },{
    path: '/admin',
    name: '后台管理系统',
    component: index,
    iconCls: 'el-icon-tickets',
    redirect: '/admin/home',
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: '/admin/home',
        name: '首页',
        component: AdminHome,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/admin/profile',
        name: '管理员个人信息',
        component: AdminProfile,
        meta: {
          requireAuth: true
        }
      }, {
        path: '/admin/users',
        name: '用户管理',
        component: userAdmin,
        meta: {
          requireAuth: true
        }
      }, {
        path: '/admin/admins',
        name: '管理员信息',
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
          name: "个人中心",
          component: PersonalCenter,
          meta: {
            requireAuth: true
          }
        },

      ]
    }
  ]
})
