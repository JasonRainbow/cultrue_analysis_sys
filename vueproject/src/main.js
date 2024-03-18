import Vue from 'vue';
// 引入element UI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App';
// 引入路由
import router from './router';
// 引入状态管理
import store from './vuex/store';

// 引入自定义图标
import './assets/icons2/iconfont.css'

// 引入自定义样式
import './assets/styles/mystyle.css'

// 引入全局样式
import './assets/styles/index.scss' // global css
import './assets/styles/ruoyi.scss' // ruoyi css

import './assets/less/style.less'

import {parseTime, timestampToTime, debounce} from "./utils/util";

// 全局方法挂载
// 引入文件下载的工具方法
import {download} from "./utils/request";
Vue.prototype.download = download
Vue.prototype.parseTime = parseTime
Vue.prototype.timestampToTime = timestampToTime
Vue.prototype.debounce = debounce
Vue.prototype.currentTime2 = new Date()

// 引入echarts
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts

import { randomNum } from "./utils/koi";
Vue.prototype.randomNum = randomNum;
// 引入DataV,将自动注册所有组件为全局组件
import dataV from '@jiaminghi/data-view';
Vue.use(dataV)

import VueAnimateNumber from 'vue-animate-number';
Vue.use(VueAnimateNumber);

// 引入轮播列表
import scroll from 'vue-seamless-scroll';
Vue.use(scroll)

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

// 配置滚动条组件
Vue.prototype.$vuescrollConfig = {
  bar: {
    background: 'rgba(199,103,8,0.47)'
  }
}

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store, //使用store vuex状态管理
    components: { App },
    template: '<App/>',
    /*data: {
        // 空的实例放到根组件下，所有的子组件都能调用
        Bus: new Vue()
    },*/
    beforeCreate() {
      Vue.prototype.$bus = this // 安装全局事件总线，$bus就是当前应用的vm
    },
})
