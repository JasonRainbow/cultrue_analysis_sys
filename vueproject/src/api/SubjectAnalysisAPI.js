import {req} from "../utils/request";


// 查询作品的得分情况
export const subjectAnalysisByWorkId = params=>{
  return req("get", "/subject-analysis/polarityByWorkIdAndSubject", params, null)
}

// 查询作品的主题
export const getSubjectsByWorkId = params=>{
  return req("get", "/subject-analysis/getSubjectsByWorkId", params, null)
}

