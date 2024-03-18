// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录

// 管理员首页
import index from '../views/index';

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
import DataAnalysisScreen from "../components/user/DataAnalysisScreen.vue";
import HomePage from "../views/user/HomePage";
import UserLogin from "../views/user/UserLogin";
import UserRegister from "../views/user/UserRegister";
import PersonalCenter from "../views/user/PersonalCenter";
import EffectPage from "../views/user/EffectPage";
import AssessmentDetailChart from "../components/user/charts/AssessmentDetailChart";
import background from "../views/user/background";
import normalQuestion from "../components/user/common/normalQuestion";
import NotLogin from "../views/user/NotLogin";
import ShowWorldMap from "../views/user/ShowWorldMap";
import TeamIntroduction from "../components/user/common/TeamIntroduction";
import PersonalRecommendation from "../views/user/PersonalRecommendation.vue";
import store from "../vuex/store";
import {hasLogin, isCurrentAdmin} from "../utils/auth";
import {Message, Notification} from "element-ui";
import HuNanColumn from "../views/user/HunanColumn";
import HunanWorkEffectPage from "../views/user/HunanWorkEffectPage";
import RelationGraph from "../components/user/charts/RelationGraph";
import HomePageLineChart from "../components/user/charts/HomePageLineChart";
import DataSourcePieChart from "../components/user/charts/DataSourcePieChart";
import  Score from "../components/user/charts/Score"
import SixEmotionPie from "../components/user/charts/pie"
import WorldMap from "../components/user/common/WorldMap.vue";
import WordCloud from "../components/user/charts/WordCloud.vue";
import NoData from "../components/user/common/NoData.vue";
import AssessmentDetailMap from "../components/user/common/AssessmentDetailMap.vue";
import DashboardPage from "../views/user/DashboardPage.vue";
import Dashboard from "../components/user/common/Dashboard.vue";
import CommentTablePage from "../components/user/common/CommentTablePage.vue";
import CommentDetailPage from "../views/user/CommentDetailPage.vue";
import HighImpactComment from "../views/user/HighImpactComment.vue";
import CommentDetail from "../components/user/common/CommentDetail.vue";
import WorkPortraitPage from "../views/user/WorkPortraitPage";

// 启用路由
Vue.use(Router);

const VueRouterPush = Router.prototype.push
Router.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

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
        requireAdmin: true
      },
      children: [
        {
          path: '/admin/home',
          name: '后台管理首页',
          component: AdminHome,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/profile',
          name: '管理员个人中心',
          component: AdminProfile,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        }, {
          path: '/admin/users',
          name: '用户信息管理',
          component: userAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        }, {
          path: '/admin/admins',
          name: '管理员信息管理',
          component: adminInfo,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/hotwork',
          name: '热点文化作品管理',
          component: HotWorkAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/monitor-work',
          name: '监测文化作品管理',
          component: MonitorWorkAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/monitor-request',
          name: '监测请求管理',
          component: MonitorRequestAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/comments',
          name: '作品评论管理',
          component: CommentAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/sentiment',
          name: '情感分析管理',
          component: SentimentAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/polarity',
          name: '情感极性分析管理',
          component: PolarityAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/admin/word-freq',
          name: '词频统计管理',
          component: WordFreqAdmin,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        },
        {
          path: '/charts/statistics',
          name: '数据可视化',
          component: statistics,
          meta: {
            requireAuth: true,
            requireAdmin: true
          }
        }]
    },
    {
      path: "/big-screen",
      name: "大屏模式",
      // component: BigScreen,
      component:DataAnalysisScreen,
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
          name: "传播效果分析",
          component: EffectPage,
          meta: {
            requireAuth: false
          },
          children:[
            // {
            //   path: "worldMap",
            //   name: "世界地图",
            //   component: WorldMap
            // },
            {
              path: "sentiment-assessment",
              name: "传播效果评估展示",
              component: AssessmentDetailChart,
              meta: {
                requireAuth: false
              }
            },
            {
              path: "no-data",
              name: "无极性情感分布数据",
              component: NoData,
              meta: {
                requireAuth: false
              }
            },
            {
              path: "sentimentAssessment",
              name: "传播效果评估展示1",
              component: AssessmentDetailMap,
              meta: {
                requireAuth: false
              }
            },
            {
              name: 'CommentDetailPage1',
              path: 'commentDetail1',
              component:CommentDetail,
              meta: {
                requireAuth: false
              },
              props: (route)=>({
                commentId : Number(route.query.commentId),
              })
            }
          ]
        },
        {
          path: "/special-column",
          name: "湖湘文化作品",
          component: HuNanColumn,
          meta: {
            requireAuth: false
          }
        },
        {
          path: "/personal",
          name: "用户个人中心",
          component: PersonalCenter,
          meta: {
            requireAuth: true,
            requireAdmin: false
          }
        },
        // {
        //   path: "/sentiment-assessment",
        //   name: "传播效果评估展示",
        //   component: AssessmentDetailChart,
        //   meta: {
        //     requireAuth: false
        //   }
        // },
        {
          path: "/background",
          name: "背景介绍",
          component: background,
          meta: {
            requireAuth: false
          }
        },
        {
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
    },
    {
      path: "/HighImpactComment",
      name: "高影响力评论",
      component: HighImpactComment,
    },
    {
      path: "/WorkPortraitPage",
      name: "作品画像",
      component: WorkPortraitPage,
    },
    {
      path: "/HunanWorkEffect",
      name: "湖南文化作品传播效果分析",
      component: HunanWorkEffectPage,
      meta: {
        requireAuth: false
      },
      redirect: "/sixEmotionPie",
      children: [
        {
          name: "作品评分图",
          path: "/scoreChart",
          component: Score,
          props: (route) => ({
            workId : Number(route.query.workId),
            height: route.query.height,
            fontColor: route.query.fontColor
          }),
          meta: {
            requireAuth: false
          }
        },
        {
          name: "词云图",
          path: "/wordCloud",
          component: WordCloud,
          props: (route) => ({
            workId : Number(route.query.workId),
            height: route.query.height,
            width: route.query.width,
            fontColor: route.query.fontColor,
            marginTop: route.query.marginTop,
            mask: Boolean(route.query.mask),
            numLimit: Number(route.query.numLimit),
          }),
          meta: {
            requireAuth: false
          }
        },
        {
          name: "语义网络分析",
          path: "/relationGraph",
          component: RelationGraph,
          props: (route) => ({
            workId : Number(route.query.workId),
            height: route.query.height
          }),
          meta: {
            requireAuth: false
          }
        },
        {
          name: "细腻分析情感分布",
          path: "/sixEmotionPie",
          component: SixEmotionPie,
          props: (route) => ({
            workId : Number(route.query.workId),
            height: route.query.height === undefined ? '560px' : route.query.height
          }),
          meta: {
            requireAuth: false
          }
        },
        {
          name: "极性情感分析",
          path: "/polarityAnalysis",
          component: HomePageLineChart,
          props: (route) => ({
            workId : Number(route.query.workId),
            height: route.query.height
          }),
          meta: {
            requireAuth: false
          }
        },
        {
          name: "数据来源图",
          path: "/dataSourceChart",
          component: DataSourcePieChart,
          props: (route) => ({
            workId : Number(route.query.workId),
            height: route.query.height,
            fontColor: route.query.fontColor
          }),
          meta: {
            requireAuth: false
          }
        },
      ]
    },
    {
      name: "DashboardPage",
      path: "/dashboard",
      component: DashboardPage,
      redirect: "/dashboard/main",
      children: [
        {
          name: 'Dashboard',
          path: "/dashboard/main",
          component: Dashboard
        },
        {
          name: 'commentTable',
          path: '/dashboard/commentTable',
          component: CommentTablePage
        }
      ]
    },
    // 查看评论详情页面
    {
      name: 'CommentDetailPage',
      path: '/commentDetail',
      component: CommentDetailPage,
      meta: {
        requireAuth: false
      },
      props: (route)=>({
        commentId : Number(route.query.commentId),
      })
    }
  ]
})

// 对路由进行权限控制
// 全局路由拦截器  前置路由守卫
router.beforeEach((to, from, next) => {
  if (to.matched.length !== 0) { // 路由能够匹配
    const authenticated = hasLogin()
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
      if (to.meta.requireAdmin) { // 判断是否需要管理员才能访问
        if (!authenticated) {
          next({
            path: "/admin/login",
            query: {redirect: to.fullPath}
          })
          return;
        }
        if (!isCurrentAdmin()) {
          Notification.error("你不是超级管理员，不能访问后台管理系统！")
          next({
            path: "/",
          })
          return;
        }
      } else {
        if (!authenticated) {
          next({
            path: "/not-login",
            query: {redirect: to.fullPath}
          })
          return;
        }
      }
      next()
    } else {
      if (to.path === "/admin/login" && authenticated && isCurrentAdmin()) { // 管理员已登录访问管理员登录页面
        next({ // 重定向到后台管理系统首页
          path: "/admin/home",
          redirect: to.fullPath
        })
        return;
      } else if (to.path === "/login" && authenticated) { // 用户已登录访问登录用户登录页面
        next({ // 重定向到网页客户端首页
          path: "/",
          redirect: to.fullPath
        })
        return;
      }
      next(); // 不需要登录，且路由合法，直接放行
    }
    if (authenticated) {
      let user = store.state.user
      if (!user) {
        let interrupt = false
        store.dispatch("GetUserInfo").then((res)=>{

        }).catch((err)=>{ // 获取用户个人信息出现错误，可能是用户认证失败
          // 退出登录
          Message.error(err.message)
        })
      }
    } else {
      store.dispatch("ClearUserInfo").then((res)=>{

      })
    }
  } else { // 没有匹配的路由，重定向到首页
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
