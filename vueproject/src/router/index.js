// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录
import login from '@/views/login';
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

// 启用路由
Vue.use(Router);

// 导出路由
export default new Router({
  routes: [{
    path: '/',
    name: '首页',
    component: login,
    hidden: true,
    meta: {
      requireAuth: false
    }
  }, {
    path: '/admin/login',
    name: '登录',
    component: Login2,
    hidden: true,
    meta: {
      requireAuth: false
    }
  }, {
    path: '/admin/login3',
    name: '登录3',
    component: login3,
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
  }]
})
