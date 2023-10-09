import Vue from 'vue';
import Vuex from 'vuex';
import {getToken, setLocalSessionItem, setToken} from "../utils/auth";
import {loginSys, loginSysAdmin} from "../api/AuthAPI";

Vue.use(Vuex);
// 登录验证
export default new Vuex.Store({
  state: {
    user: false,
    admin: false,
    token: getToken() // 从cookie中获取token
  },
  mutations: {
    SET_TOKEN(state, token) { // 保存token
      state.token = token
    },
    SET_USER(state, user) { // 保存用户信息
      state.user = user
    },
    // 登录
    user_login(state, loginUser) { // 用户登录
      setToken(loginUser.token)
      state.user = loginUser.user;
      setLocalSessionItem("user", state.user)
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
  },
  actions: {
    Login({commit}, { userInfo, adminLogin }) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      let loginFunc;
      if (adminLogin) { // 是否是管理员登录
        loginFunc = loginSysAdmin
      } else {
        loginFunc = loginSys
      }
      return new Promise((resolve, reject)=>{
        loginFunc({username,password}).then((res)=>{
          setToken(res.data.token)
          setLocalSessionItem("user", res.data.user)
          commit('SET_TOKEN', res.data.token)
          commit('SET_USER', res.data.user)
          resolve()
        }).catch((err)=>{
          reject(err)
        })
      })
    },

  }
})
