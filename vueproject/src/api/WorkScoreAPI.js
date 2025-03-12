import {req} from "../utils/request";

// 查询作品的得分情况
export const workScoreByWorkId = params=>{
  return req("get", "/work-score/findByWorkId", params, null)
}
