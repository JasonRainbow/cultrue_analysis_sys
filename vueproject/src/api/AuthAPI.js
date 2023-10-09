import {req} from '../utils/request'

// 登录
export const loginSys = (params)=>{
  return req("post", "/login", null, params)
}

// 退出登录
export const logoutSys = ()=>{
  return req("get", "/logout")
}

// 注册
export const registerSys = (params)=>{
  return req("post", "/register", null, params)
}
