import {req} from '../utils/request'

// 查询所有的评论信息
export const getAllComments = ()=>{
  return req("get", "/comment/all", null, null);
}

// 根据id查询指定的评论信息
export const getCommentById = (params)=>{
  return req("get", "/comment/id/" + params, null, null);
}

// 查询所有的国家列表
export const getCountries = (params)=>{
  return req("get", "/comment/countries", params, null);
}

// 查询所有的平台列表
export const getPlatforms = ()=>{
  return req("get", "/comment/platforms", null, null);
}

// 带搜索关键词的分页查询评论信息 workId、searchContent、searchCountry、searchPlatform、searchTime、pageNum、pageSize
export const getCommentsByPage = (params)=>{
  return req("get", "/comment/byPage", params, null);
}

// 带搜索关键词的分页查询评论信息 searchWorkName、searchContent、searchCountry、searchPlatform、searchTime、pageNum、pageSize
export const getCommentsByPage2 = (params)=>{
  return req("get", "/comment/byPage2", params, null);
}

// 查询热点评论 分页
export const getHotCommentsByPage = (params)=>{
  return req("get", "/comment/getHotComment", params, null);
}

// 根据id删除指定评论
export const deleteComment = (params)=>{
  return req("delete", "/comment/delete/" + params, null, null);
}

// 新增评论信息
export const addComment = (params)=>{
  return req("post", "/comment/add", null, params);
}

// 修改热点文化作品信息
export const updateComment = (params)=>{
  return req("put", "/comment/update", null, params);
}

//查询作品在各个国家的评论数量
export const getCountryCommentsNumByWorkId = (params)=>{
  return req("get", "/comment/getCommentNum", params, null);
}
