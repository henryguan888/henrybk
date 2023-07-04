import request from '@/utils/request'
import exportExcel from '@/utils/export'

const API = '/admin/system/sysLoginLog'

export default {
  // 分页查询
  getPageList(pageNum, pageSize, searchObj) {
    return request({
      url: `${API}/getPageList/${pageNum}/${pageSize}`,
      method: 'get',
      params: searchObj
    })
  },
  
}