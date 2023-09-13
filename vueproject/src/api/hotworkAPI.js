import {req} from '../utils/request'

// 热点文化作品相关的接口

// 获取所有的热点文化作品
export const getAllHotWorks = ()=>{
  return req("get", "/hot-work/all", null, null);
}

// 根据id获取指定的热点文化作品
export const getHotWorkById = (params)=>{
  return req("get", "/hot-work/id/" + params, null, null);
}

// 分页 模糊查询热点文化作品
export const getHotWorkByPage = (params)=>{
  return req("get", "/hot-work/byPage", params, null);
}

// 根据id删除指定热点作品
export const deleteHotWorkById = (params)=>{
  return req("delete", "/hot-work/delete/" + params, null, null);
}

// 新增热点文化作品
export const addHotWork = (params)=>{
  return req("post", "/hot-work/add", null, params);
}

// 修改热点文化作品信息
export const updateHotWork = (params)=>{
  return req("put", "/hot-work/update", null, params);
}

// 修改热点文化作品信息
export const updateAllHotWorks = (params)=>{
  return req("get", "/hot-work/updateHotWork", params, null);
}

