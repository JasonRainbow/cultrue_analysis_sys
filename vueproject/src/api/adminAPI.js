import {req} from '../utils/request'

// 管理员登录的接口
export const adminLogin = params=>{
  return req("post", "/admin/login", null, params);
}

// 修改管理员密码的接口
export const updateAdminPwd = params=>{
  return req("put", "/admin/pass", null, params);
}

// 重置管理员密码的接口
export const resetAdminPwd = params=>{
  return req("put", "/admin/resetPwd", {adminId: params}, null);
}

// 管理员注册的接口
export const adminRegister = params=>{
  return req("post", "/admin/register", null, params);
}

// 保存（更新）管理员信息的接口
export const adminUpdate = params=>{
  return req("put", "/admin/update", null, params);
}

// 查询指定id的管理员 的接口
export const adminGetById = params=>{
  return req("get", "/admin/id/" + params, null, null);
}

// 查询所有管理员的接口
export const adminGetAll = ()=>{
  return req("get", "/admin/all", null, null);
}

// 分页模糊查询管理员 接口
export const adminGetByPage = (params)=>{
  return req("get", "/admin/byPage", params, null);
}

// 获取当前登录的管理员个人信息的接口
export const getAdminProfile = ()=>{
  return req("get", "/admin/profile", null, null)
}

// 上传管理员头像的接口
export const uploadAdminAvatar = (file)=>{
  return req("post", "/admin/uploadAvatar", null, file)
}

// 删除管理员的接口
export const deleteAdmin = (params)=>{
  return req("delete", "/admin/delete/" + params, null, null);
}

