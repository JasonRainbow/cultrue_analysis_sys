import {req} from "../utils/request";

// 上传文件到服务器
export const fileUpload = (params)=>{
  return req("post", "/files/upload", null, params)
}
