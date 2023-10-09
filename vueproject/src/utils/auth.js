import Cookies from 'js-cookie'

const tokenKey = "UserToken"

export const getToken = ()=>{
  return Cookies.get(tokenKey)
}

export const setToken = (token)=>{
  return Cookies.set(tokenKey, token, {expires: 7}) // 有效期为7天
}

export const removeToken = ()=>{
  return Cookies.remove(tokenKey)
}

export const parseLocalSession = (key)=>{
  return JSON.parse(localStorage.getItem(key))
}

export const setLocalSessionItem = (key,value)=>{
  return localStorage.setItem(key, JSON.stringify(value))
}

export const hasLogin = ()=>{
  return getToken()
}

export const isCurrentAdmin = ()=>{
  return parseLocalSession("user").isAdmin
}
