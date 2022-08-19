import axios from 'axios'
import { TokenKey, getToken } from '@/control/auth';

const service = axios.create({
  timeout: 50000, // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    if (!config.headers[TokenKey]) {
      config.headers[TokenKey] = getToken();
    }
 
    return config;
  },
  err => Promise.reject(err)
)


// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.errno === 501) {
      return res;
    } else if (res.errno === 502) {
      return res;
    } if (res.errno === 401) {
      return res;
    } if (res.errno === 402) {
      return res;
    } else if (res.errno !== 0) {
      return res;
    } else {
      return res;
    }
  }, error => {
    return {
      errno: 400,
      errmsg: error.message,
    }
  }
)

export default service
