import {req} from '../utils/request'

// 查询所有的监测作品的信息
export const getAllMonitorWorks = ()=>{
  return req("get", "/monitor-work/all", null, null);
}

// 根据id查询指定监测作品的信息
export const getMonitorWorkById = (params)=>{
  return req("get", "/monitor-work/id/" + params, null, null);
}

// 分页 模糊查询 带关键词 searchName、searchCategory、pageNum、pageSize
export const getMonitorWorkByPage = (params)=>{
  return req("get", "/monitor-work/byPage", params, null);
}

// 根据id删除指定监测作品
export const deleteMonitorWork = (params)=>{
  return req("delete", "/monitor-work/delete/" + params, null, null);
}

// 新增监测文化作品
export const addMonitorWork = (params)=>{
  return req("post", "/monitor-work/add", null, params);
}

// 修改热点文化作品信息
export const updateMonitorWork = (params)=>{
  return req("put", "/monitor-work/update", null, params);
}

// 通过用户的id获取用户申请的监测作品信息  请求参数userId
export const getMonitorWorkByUserId = (params)=>{
  return req("get", "/monitor-work/byUserId", params, null);
}

export const getMonitorWorkByUserIdPaging = (params)=>{
  return req("get", "/monitor-work/byUserIdPaging", params, null);
}

// 获取指定文化作品的平台来源统计信息  请求参数 workId
export const countMonitorWorkPlatform = (params)=>{
  return req("get", "/monitor-work/countPlatform", params, null);
}


// 获取指定用户的推荐作品列表
export const getRecommendWorksByUserId = (params)=>{
  return req("get", "/monitor-work/recommendByUserId", params, null)
}

// 获取所有影视作品类别
export const getAllVideoCategories = ()=>{
  return req("get", "/monitor-work/getAllVideoCategories")
}

// 获取所有文学作品类别
export const getAllBookCategories = ()=>{
  return req("get", "/monitor-work/getAllBookCategories")
}

// 获取所有的地域
export const getAllOrigin = ()=>{
  return req("get", "/monitor-work/getAllOrigin")
}

// 分页查询湖南的文化作品
export const getHunanWorks = (params)=>{
  return req("post", "/monitor-work/getHunanWork", null, params)
}

export const getAllSubcategory = (params)=>{
  return req("get", "/monitor-work/getWorkByCategory", null, null)
}

