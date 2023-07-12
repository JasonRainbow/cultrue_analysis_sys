import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
// 登录验证
export default new Vuex.Store({
  state: {
    user: false,
    admin: false
  },
  mutations: {
    // 登录
    user_login(state, user) { // 用户登录
      state.user = user;
      localStorage.setItem("user", JSON.stringify(user));
    },
    // 退出
    user_logout(state, user) { // 用户退出登录
      state.user = "";
      localStorage.removeItem("user");
    },
    admin_login(state, admin) { // 管理员登录
      state.admin = admin;
      localStorage.setItem("admin", JSON.stringify(admin))
    },
    admin_logout(state, admin) { // 管理员退出登录
      state.admin = "";
      localStorage.removeItem("admin"); // 清空本地缓存
    }
  }
})
