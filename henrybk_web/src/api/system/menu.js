import request from '@/utils/request'
import exportExcel from '@/utils/export'

const API = '/admin/system/sysMenu'

export default {

  //获取权限(菜单/功能)列表
  findNodes(searchObj) {
    return request({
      url: `${API}/getTree`,
      method: 'get',
      params: searchObj
    })
  },
  //添加
  save(data) {
    return request({
      url: `${API}/save`,
      method: 'post',
      data: data
    })
  },
  //根据id修改
  updateById(data) {
    return request({
      url: `${API}/updateById`,
      method: 'put',
      data: data
    })
  },
  //删除
  removeById(id) {
    return request({
      url: `${API}/removeById/${id}`,
      method: "delete"
    })
  },
 
}