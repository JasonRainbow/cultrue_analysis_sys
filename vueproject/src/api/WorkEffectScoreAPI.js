import {req} from '../utils/request'

// 查询作品的在某个平台上的传播效果得分情况
export const getWorkEffectScore = params=>{
  return req("get", "/workEffectScore/findByWorkIdAndPlatform", params, null)
}

// 查询作品的综合传播效果得分
export const findWorkEffectScore = params=>{
  return req("get", "/workEffectScore/getByWorkId", params, null)
}

// 查询作品的综合传播效果得分
export const getSameCategoryEffectScore = params=>{
  return req("get", "/workEffectScore/getSameCategoryEffectScoreByWorkId", params, null)
}

