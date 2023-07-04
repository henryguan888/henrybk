package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysUserRole;
import com.henrybk.vo.vo.sys.AssignRoleVo;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.vo.sys.SysUserVo;

import java.util.List;
import java.util.Map;

/**
 * @description 用户角色关联表 服务类
 * @author Henry
 * @since 2023-05-20
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    Map<String, Object> toAssign(Long userId);

    void doAssign(AssignRoleVo assignRoleVo);

    Page<SysUserVo> getSysUserByRoleId(Long roleId, Integer pageNum, Integer pageSize, SysUserQueryVo sysUserQueryVo);

    Page<SysUserVo> getOtherSysUserByRoleId(Long roleId, Integer pageNum, Integer pageSize, SysUserQueryVo sysUserQueryVo);

    void saveAssignUser(Long roleId, List<Long> userIds);

    void cancelAssignUserById(Long roleId, Long userId);

    void batchCancelAssignUser(Long roleId, List<Long> userIds);

}
