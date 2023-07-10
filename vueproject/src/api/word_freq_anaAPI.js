import {req} from '../utils/request'

// 主题词词频统计信息的接口

// 查询所有的词频统计结果
export const getAllWordFreqAna = ()=>{
  return req("get", "/word-freq/all", null, null);
}

// 根据id查询词频统计结果
export const getWordFreqById = (params)=>{
  return req("get", "/word-freq/id/" + params, null, null);
}

// 分页 模糊查询 带关键词 searchWorkName、searchCountry、searchPlatform、searchTime、pageNum、pageSize
export const getWordFreqByPage = (params)=>{
  return req("get", "/word-freq/byPage", params, null);
}

// 查询指定作品的词频统计结果  请求参数：searchWorkId、searchTime、searchCountry、searchPlatform
export const getWordFreqRes = (params)=>{
  return req("get", "/word-freq/query-res", params, null);
}

// 根据id删除指定词频分析结果
export const deleteWordFreq = (params)=>{
  return req("delete", "/word-freq/delete/" + params, null, null);
}

// 新增词频分析结果
export const addWordFreq = (params)=>{
  return req("post", "/word-freq/add", null, params);
}

// 修改词频统计结果
export const updateWordFreq = (params)=>{
  return req("put", "/word-freq/update", null, params);
}

