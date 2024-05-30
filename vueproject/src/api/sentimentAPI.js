import {req} from '../utils/request'

// 细腻情感分析接口
export const getMonthlySentiment = (params)=>{
  return req("get", "/sentiment/countMonthly", params, null);
}
// 查询所有的情感分析结果
export const getAllSentiment = ()=>{
  return req("get", "/sentiment/all", null, null);
}

// 根据情感分析id查询情感分析结果
export const getSentimentById = (params)=>{
  return req("get", "/sentiment/id/" + params, null, null);
}

// 分页 模糊查询 带关键词 searchWorkName、searchCountry、searchPlatform、searchTime、pageNum、pageSize
export const getSentimentByPage = (params)=>{
  return req("get", "/sentiment/byPage", params, null);
}

// 精确查询
export const querySentiment = (params)=>{
  return req("get", "/sentiment/query-list", params, null);
}

export const querySentimentByWorkIdAndTime = (params)=>{
  return req("get", "/sentiment/getWorldSentiment", params, null);
}

// 统计不同情感倾向的评论数  请求参数：workId、country、postTime
export const sentimentCountDaily = (params)=>{
  return req("get", "/sentiment/countDaily", params, null);
}

// 根据id删除指定情感分析结果
export const deleteSentiment = (params)=>{
  return req("delete", "/sentiment/delete/" + params, null, null);
}

// 新增情感分析结果
export const addSentiment = (params)=>{
  return req("post", "/sentiment/add", null, params);
}

// 修改情感分析结果
export const updateSentiment = (params)=>{
  return req("put", "/sentiment/update", null, params);
}

//
