import {req2} from '../utils/request'

export const get_gram_net = params=>{
  return req2("get", "/generate_network", params, null);
}
