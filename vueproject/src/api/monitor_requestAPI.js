import {req} from '../utils/request'

// 查询所有的监测请求信息
export const getAllMonitorRequests = ()=>{
  return req("get", "/monitor-request/all", null, null);
}

// 根据监测请求id查询监测请求信息
export const getMonitorRequestById = (params)=>{
  return req("get", "/monitor-request/id/" + params, null, null);
}

// 根据用户id查询监测请求信息 分页  请求参数：userId、pageNum、pageSize
export const getMonitorRequestByUserId = (params)=>{
  return req("get", "/monitor-request/byUserId", params, null);
}

// 分页 搜索查询 搜索关键词：用户名、作品名
export const getMonitorRequestByPage = (params)=>{
  return req("get", "/monitor-request/byPage", params, null);
}

// 根据id删除指定监测请求
export const deleteMonitorRequest = (params)=>{
  return req("delete", "/monitor-request/delete/" + params, null, null);
}

// 新增监测请求  首先把作品插入监测作品表（如果该作品原来不在监测作品表中）再插入监测请求表
export const addMonitorRequest = (params)=>{
  return req("post", "/monitor-request/add", null, params);
}

// 修改监测请求信息
export const updateMonitorRequest = (params)=>{
  return req("put", "/monitor-request/update", null, params);
}

//
