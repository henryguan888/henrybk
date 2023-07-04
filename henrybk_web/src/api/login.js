import request from '@/utils/request'

const API = '/admin/system/userLogin'

// 登录
export function login(data) {
  return request({
    url: `${API}/login`,
    method: 'post',
    data
  })
}

// 获取个人信息
export function getInfo() {
  return request({
    url: `${API}/getInfo`,
    method: 'get',
  })
}

// 退出登录
export function logout() {
  return request({
    url: `${API}/logout`,
    method: 'post'
  })
}

// 个人中心获取
export function getUserInfo() {
  return request({
    url: `${API}/getUserInfo`,
    method: 'get',
  })
}

// 修改基本信息
export function updateUserInfo(data) {
  return request({
    url: `${API}/updateUserInfo`,
    method: 'put',
    data
  })
}

// 修改密码
export function updatePwd(data) {
  return request({
    url: `${API}/updatePwd`,
    method: 'put',
    data
  })
}

// 获取系统的用户量、登录量、操作量、定时任务量
export function getSystemData() {
  return request({
    url: `${API}/getSystemData`,
    method: 'get',
  })
}

// 获取每个角色对应用户数
export function getUserByRole() {
  return request({
    url: `${API}/getUserByRole`,
    method: 'get',
  })
}

// 获取近一周的登录和操作数据
export function getLoginAndOperData(searchDate) {
  return request({
    url: `${API}/getLoginAndOperData/${searchDate}`,
    method: 'get'
  })
}

