import {req, req2} from '../utils/request'

// 获取共现语义网络图信息的接口
export const get_gram_net = params=>{
  return req2("get", "/generate_network", params, null);
}

// 爬取作品评论的接口
export const scrap_comment = params=>{
  return req("get", "/crawler/crawl-comments", params, null);
}

// 爬取作品得分的接口
export const scrap_score = params=>{
  return req("get", "/crawler/crawl-scores", params, null);
}

// 词频统计
export const count_word_freq = params=>{
  return req("get", "/analysis/count-word-freq", params, null);
}

// 情感极性分析
export const analyze_polarity = params=>{
  return req("get", "/analysis/analyze-polarity", params, null);
}

// 细腻情感分析
export const analyze_sentiment = params=>{
  return req("get", "/analysis/analyze-sentiment", params, null);
}

// 主题情感分析
export const analyze_subject = params=>{
  return req("get", "/analysis/analyze-subject", params, null)
}
