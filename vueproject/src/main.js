import Vue from 'vue';
// 引入element UI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App';
// 引入路由
import router from './router';
// 引入状态管理
import store from './vuex/store';
// 引入icon
import './assets/icon/iconfont.css'
import './assets/icons' // icon

// 引入全局样式
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css


// 引入element-ui图标
import 'element-ui/lib/theme-chalk/index.css'

import SvgIcon from './components/SvgIcon' // svg component

import {parseTime, timestampToTime, debounce} from "./utils/util";


// 全局方法挂载
// 引入文件下载的工具方法
import {download} from "./utils/request";
Vue.prototype.download = download

Vue.prototype.parseTime = parseTime
Vue.prototype.timestampToTime = timestampToTime
Vue.prototype.debounce = debounce

// 引入echarts
import echarts from 'echarts'
Vue.prototype.$echarts = echarts

import axios from 'axios';
Vue.prototype.$axios = axios;

Vue.config.productionTip = false;

// 使用element UI
Vue.use(ElementUI);
// 过滤器
import * as custom from './utils/util'

Object.keys(custom).forEach(key => {
    Vue.filter(key, custom[key])
})

// 路由拦截器
router.beforeEach((to, from, next) => {
    if (to.matched.length !== 0) {
        if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
            if (Boolean(localStorage.getItem("admin"))) { // 通过vuex state获取当前的user是否存在
              const admin = JSON.parse(localStorage.getItem("admin"));
              console.log(admin)
              if (admin.avatar == null || admin.avatar === "") {
                admin.avatar = require("./assets/img/avatar.jpeg")
              }
              store.state.admin = admin;
              next();
            } else {
                next({
                    path: '/admin/login',
                    query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
                })
            }
        } else {
            if (Boolean(localStorage.getItem("admin"))) { // 判断是否登录
                if (to.path != "/" && to.path != "/login") { //判断是否要跳到登录界面
                    next();
                } else {
                    /**
                     * 防刷新，如果登录，修改路由跳转到登录页面，修改路由为登录后的首页
                     */
                    next({
                        path: '/admin/home'
                    })
                }
            } else {
                next();
            }

        }
    } else {
        next({
            path: '/admin/login',
            query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
        })
    }
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store, //使用store vuex状态管理
    components: { App },
    template: '<App/>',
    data: {
        // 空的实例放到根组件下，所有的子组件都能调用
        Bus: new Vue()
    }

})
