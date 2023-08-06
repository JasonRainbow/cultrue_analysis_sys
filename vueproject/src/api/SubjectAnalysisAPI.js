import {req} from "../utils/request";


// 查询作品的得分情况
export const subjectAnalysisByWorkId = params=>{
  return req("get", "/subject-analysis/polarityByWorkIdAndSubject", params, null)
}

