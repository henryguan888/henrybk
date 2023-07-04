import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 15000 // 请求超时时间 毫秒
})

// 请求拦截
service.interceptors.request.use(
  config => {
    // 判断store中是否存在token
    if (store.getters.token) {
      // 读取token，并将token添加到headers头部信息中
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log("请求异常",error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截
service.interceptors.response.use(
  // 后端返回的数据
  response => {
    const res = response.data
    
    // 如果后端返回的状态码不是20000，返回错误信息
    if (res.code !== 20000) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: 非法token; 50012: 已在其他客户端登录; 50014: token过期;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox.confirm('用户登录信息过期，请重新登录！', '系统提示', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
