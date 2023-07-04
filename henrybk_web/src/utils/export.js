import axios from 'axios'
import store from '@/store'
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  timeout: 5000 
})

//请求拦截
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
    console.log("请求异常", error) // for debug
    return Promise.reject(error)
  }
)

//响应拦截
service.interceptors.response.use(
  res => {
    try {
      // 处理返回的文件流
      let blob = new Blob([res.data], { type: res.data.type });
      //获取fileName,截取content-disposition的filename；按=分割，取最后一个
      const fileName = decodeURI(res.headers['content-disposition'].split("=")[1], "UTF-8");
      let downloadElement = document.createElement("a");
      let href = window.URL.createObjectURL(blob); //创建下载的链接
      downloadElement.href = href;
      downloadElement.download = fileName; //下载后文件名
      document.body.appendChild(downloadElement);
      downloadElement.click(); //点击下载
      document.body.removeChild(downloadElement); //下载完成移除元素
      window.URL.revokeObjectURL(href); //释放blob
    } catch (error) {
      Message({
        message: "导出失败",
        type: 'error',
        duration: 5 * 1000
      })
    }
    
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: "导出失败",
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
