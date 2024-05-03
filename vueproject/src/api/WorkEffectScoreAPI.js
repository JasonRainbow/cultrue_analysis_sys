import {req} from '../utils/request'

// 查询作品的在某个平台上的传播效果得分情况
export const getWorkEffectScore = params=>{
  return req("get", "/workEffectScore/findByWorkIdAndPlatform", params, null)
}
