import request from '@/utils/request'
import exportExcel from '@/utils/export'

const API = '/admin/system/sysRole'

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
  // 导出
  download() {
    return exportExcel({
      url: `${API}/download`,
      method: 'get',
      responseType: 'blob'
    })
  },
  // 获取角色的菜单树
  toAssignMenu(roleId) {
    return request({
      url: `${API}/toAssignMenu/${roleId}`,
      method: 'get'
    })
  },
  // 根据角色分配菜单
  doAssignMenu(assginMenuVo) {
    return request({
      url: `${API}/doAssignMenu`,
      method: "post",
      data: assginMenuVo
    })
  },

  // 根据角色编号获取用户
  getSysUserByRoleId(roleId,pageNum, pageSize, searchObj) {
    return request({
      url: `${API}/getSysUserByRoleId/${roleId}/${pageNum}/${pageSize}`,
      method: 'get',
      params: searchObj
    })
  },
  // 根据角色编号获取还未授权的用户
  getOtherSysUserByRoleId(roleId,pageNum, pageSize, searchObj) {
    return request({
      url: `${API}/getOtherSysUserByRoleId/${roleId}/${pageNum}/${pageSize}`,
      method: 'get',
      params: searchObj
    })
  },
  // 添加授权用户提交
  saveAssignUser(roleId,userIds) {
   return request({
      url: `${API}/saveAssignUser/${roleId}/${userIds}`,
      method: 'post',
    })
  },
  // 取消授权
  cancelAssignUserById(roleId,userId) {
    return request({
      url: `${API}/cancelAssignUserById/${roleId}/${userId}`,
      method: 'delete'
    })
  },
  // 批量取消授权
  batchCancelAssignUser(roleId,userIds) {
    return request({
      url: `${API}/batchCancelAssignUser/${roleId}/${userIds}`,
      method: 'delete'
    })
  },
  
}