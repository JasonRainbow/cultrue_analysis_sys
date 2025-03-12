import {req} from '../utils/request'

// 查询所有的评论信息
export const getEffectPredictionByWorkIdAndYear = (params)=>{
  return req("get", "/effect-prediction/findByWorkIdAndYear", params, null);
}
