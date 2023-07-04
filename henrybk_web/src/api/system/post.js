import request from '@/utils/request'
import exportExcel from '@/utils/export'

const API = '/admin/system/sysPost'

export default {
  // 分页查询
  getPageList(pageNum, pageSize, searchObj) {
    return request({
      url: `${API}/getPageList/${pageNum}/${pageSize}`,
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
  // 根据id查询
  getById(id) {
    return request({
      url: `${API}/getById/${id}`,
      method: 'get'
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
  // 根据id修改状态
  updateStatus(id, status) {
    return request({
      url: `${API}/updateStatus/${id}/${status}`,
      method: 'get'
    })
  },
  //根据id逻辑删除
  removeById(id) {
    return request({
      url: `${API}/removeById/${id}`,
      method: 'delete'
    })
  },
  //批量删除
  batchRemove(ids) {
    return request({
      url: `${API}/batchRemove/${ids}`,
      method: 'delete'
    })
  },
  //导出
  download() {
    return exportExcel({
      url: `${API}/download`,
      method: 'get',
      responseType: 'blob'
    })
  },
}