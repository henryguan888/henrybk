import request from '@/utils/request'

const API = '/admin/system/sysDictData'

export default {
  // 获取权限(菜单/功能)列表
  findNodes(searchObj) {
    return request({
      url: `${API}/getTree`,
      method: 'get',
      params: searchObj
    })
  },
  // 添加
  save(data) {
    return request({
      url: `${API}/save`,
      method: 'post',
      data: data
    })
  },
  // 根据id修改
  updateById(data) {
    return request({
      url: `${API}/updateById`,
      method: 'put',
      data: data
    })
  },
  // 根据id逻辑删除
  removeById(id) {
    return request({
      url: `${API}/removeById/${id}`,
      method: 'delete'
    })
  },
  // 批量删除
  batchRemove(ids) {
    return request({
      url: `${API}/batchRemove/${ids}`,
      method: 'delete'
    })
  },
  
  
}