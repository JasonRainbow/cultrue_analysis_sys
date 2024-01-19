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

//查询某个作品在某个国家的评论数量
export const getCommentNumByWorkIdAndCountry = (params)=>{
  return req("get", "/comment/getCommentNumByWorkIdAndCountry", params, null);
}

// 分页查询不同语言评论的数量
export const getCommentNumByLanguage = (params)=>{
  return req("get", "/comment/getCommentNumByLanguage", params)
}

// 分页查询不同平台上的评论量
export const getCommentNumByPlatform = (params)=>{
  return req("get", "/comment/getCommentNumByPlatform", params)
}

// 分页查询不同子类型作品的评论数量
export const getCommentNumBySubCategory = (params)=>{
  return req("get", "/comment/getCommentNumBySubCategory", params)
}

// 查询所有不同子类型作品的评论数量
export const getAllCommentNumBySubCategory = (params)=>{
  return req("get", "/comment/getAllCommentNumBySubCategory", params)
}

// 根据作品id查询不同平台的高影响力评论
export const getHighCommentByPlatform = (params)=>{
  return req("get", "/comment/getHighCommentByPlatform", params)
}

// 查询近年的评论数量变化
export const getCommentNumByWorkIdAndYear = (params)=>{
  return req("get", "/comment/getCommentNumByWorkIdAndYear", params)
}

//根据作品id和国家查询在该国家对该作品发布过评论的所有平台
export const getPlatformByWorkIdAndCountry = (params)=>{
  return req("get", "/comment/getPlatformByWorkIdAndCountry", params)
}

//根据平台作品id和平台和国家查询极性评论
export const getPolarityComment = (params)=>{
  return req("get", "/comment/getPolarityComment", params)
}
