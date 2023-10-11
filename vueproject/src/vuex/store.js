import Vue from 'vue';
import Vuex from 'vuex';
import {
  getToken,
  parseLocalStorageItem,
  removeLocalStorageUser,
  removeToken,
  setLocalStorageItem,
  setToken
} from "../utils/auth";
import {loginSys, loginSysAdmin, logoutSys} from "../api/AuthAPI";
import {getUserProfile, userUpdate} from "../api/userAPI";
import {fileUpload} from "../api/fileAPI";
import {Message} from "element-ui";

Vue.use(Vuex);
// 登录验证
export default new Vuex.Store({
  state: {
    user: parseLocalStorageItem("user"),
    token: getToken() // 从cookie中获取token
  },
  mutations: {
    SET_TOKEN(state, token) { // 保存token
      state.token = token
    },
    SET_USER(state, user) { // 保存用户信息
      state.user = user
    },
  },
  actions: {
    // 登录系统
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
          setLocalStorageItem("user", res.data.user)
          commit('SET_TOKEN', res.data.token)
          commit('SET_USER', res.data.user)
          resolve()
        }).catch((err)=>{
          reject(err)
        })
      })
    },
    // 退出系统
    Logout({ commit }) {
      return new Promise((resolve, reject)=>{
        logoutSys().then(()=>{
          this.dispatch("ClearUserInfo")
          resolve()
        }).catch((err)=>{
          reject(err) // 出现错误，拒绝
        })
      })
    },
    // 获取用户信息
    GetUserInfo({ commit }) {
      return new Promise((resolve, reject)=>{
        getUserProfile().then((res)=>{
          const user = res.data // 用户信息
          commit("SET_USER", user) // 提交用户信息到store
          setLocalStorageItem("user", user) // 将用户信息保存到localStorage中
          resolve(res)
        }).catch((err)=>{
          reject(err)
        })
      })
    },
    ClearUserInfo({ commit }) {
      removeToken() // 清除cookies中的token
      removeLocalStorageUser() // 清除localStorage中存放的用户信息
      commit("SET_TOKEN", "") // 清除vuex中的token
      commit("SET_USER", null) // 清除vuex中的用户信息
    },
    uploadAvatar({commit, state}, formData) {
      return new Promise((resolve, reject)=>{
        fileUpload(formData).then(res => { // 上传用户头像到本地服务器
          if (res.code === "0") {
            let store_user = state.user
            store_user.avatar = res.data
            setLocalStorageItem("user", store_user)
            commit("SET_USER", store_user)
            userUpdate({id: store_user.id, avatar: store_user.avatar}).then((res)=>{ // 保存用户的头像信息
              if (res.code === "0") {
                Message.success("修改成功");
              } else {
                Message.error("修改失败！" + res.msg)
              }
            })
          } else {
            console.log(res.msg)
          }
          resolve(res)
        }).catch((err)=>{
          reject(err)
        });
      })
    }
  },
  getters: {
    // 获取用户头像
    avatar: state=>{
      if (state.user) {
        if (state.user.avatar) { // 判断用户是否设置了自己的头像
          return state.user.avatar
        } else {
          return require("../assets/img/avatar.jpeg")
        }
      } else {
        return ""
      }
    },
    // 获取用户的昵称
    nickname: state=>{
      return state.user && state.user.nickname ? state.user.nickname : "未知姓名"
    }
  }
})
