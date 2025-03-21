import {req} from '../utils/request'

// 情感极性分析接口

// 查询所有的情感极性分析结果
export const getAllPolarity = ()=>{
  return req("get", "/polarity/all", null, null);
}

// 根据id查询情感极性分析结果
export const getPolarityById = (params)=>{
  return req("get", "/polarity/id/" + params, null, null);
}

// 分页 模糊查询 带关键词 searchWorkName、searchCountry、searchPlatform、searchTime、pageNum、pageSize
export const getPolarityByPage = (params)=>{
  return req("get", "/polarity/byPage", params, null);
}

// 精确查询
export const queryPolarity = (params)=>{
  return req("get", "/polarity/query-list", params, null);
}

// 统计不同情感极性的评论数
export const queryPolarityDistribute = (params)=>{
  return req("get", "/polarity/getDistribute", params, null);
}

// 统计不同情感极性的评论数  请求参数：workId、country、postTime
export const polarityCountDaily = (params)=>{
  return req("get", "/polarity/countDaily", params, null);
}

// 统计不同情感极性的评论数  时间段  请求参数：workId、country、startTime、dayInterval
export const polarityCountDayInterval = (params)=>{
  return req("get", "/polarity/countDayInterval", params, null);
}

// 统计最近一年的情感极性分析结果
export const polarityCountMonthInterval = (params)=>{
  return req("get", "/polarity/countMonthInterval", params, null);
}

// 查询国家列表
export const getPolarityCountries = (params)=>{
  return req("get", "/polarity/getCountries", params, null);
}

// 根据id删除指定情感极性分析结果
export const deletePolarity = (params)=>{
  return req("delete", "/polarity/delete/" + params, null, null);
}

// 新增情感极性分析结果
export const addPolarity = (params)=>{
  return req("post", "/polarity/add", null, params);
}

// 修改情感极性分析结果
export const updatePolarity = (params)=>{
  return req("put", "/polarity/update", null, params);
}

// 获取一年的极性情感分布
export const getYearPolarity = (params)=>{
  return req("get", "/polarity/countYearPolarity", params)
}

// 获取某个月的极性情感分析结果
export const getMonthAnalysisResult = (params)=>{
  return req("get", "/polarity/getMonthAnalysisResult", params)
}

// 查询世界极性情感分布
export const getWorldPolarityDistribute = (params)=>{
  return req("get", "/polarity/getWorldPolarity", params)
}

// 查询世界极性情感分布  指定年份
export const getWorldPolarityDistributeByYear = (params)=>{
  return req("get", "/polarity/getWorldPolarityByYear", params)
}
