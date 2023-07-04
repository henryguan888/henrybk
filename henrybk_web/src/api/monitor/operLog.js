import request from '@/utils/request'

const API = '/admin/system/sysOperLog'

export default {
  // 分页查询
  getPageList(pageNum, pageSize, searchObj) {
    return request({
      url: `${API}/getPageList/${pageNum}/${pageSize}`,
      method: 'get',
      params: searchObj
    })
  }
}