import request from '@/utils/request'

const API = '/admin/system'

export default {

  // 获取枚举List
  getEnumList(clazzName) {
    clazzName = clazzName === null || clazzName === '' ? 'StatusEnum' : clazzName;
    return request({
      url: `${API}/sysCommon/getEnumList/${clazzName}`,
      method: 'get'
    })
  },
  
  // 获取岗位
  getPostList() {
    return request({
      url: `${API}/sysPost/getPostList`,
      method: 'get'
    })
  },
  // 获取角色
  getRoleList() {
    return request({
      url: `${API}/sysRole/getRoleList`,
      method: 'get'
    })
  },
  // 获取字典类型
  getDictTypeList() {
    return request({
      url: `${API}/sysDictType/getDictTypeList`,
      method: 'get'
    })
  },
  // 获取模块列表
  getModuleList() {
    return request({
      url: `${API}/sysMenu/getModuleList`,
      method: 'get'
    })
  },
  // 获取系统配置List
  getConfigList(configKey) {
    return request({
      url: `${API}/sysConfig/getConfigList/${configKey}`,
      method: 'get'
    })
  },

}