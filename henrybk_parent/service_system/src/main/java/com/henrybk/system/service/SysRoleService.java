package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysRole;
import com.henrybk.vo.query.sys.SysRoleQueryVo;

import javax.servlet.http.HttpServletResponse;


/**
 * @description 角色信息表 服务类
 * @author Henry
 * @since 2023-05-18
 */
public interface SysRoleService extends IService<SysRole> {

    Page<SysRole> getSysRolePage(Integer pageNum, Integer pageSize, SysRoleQueryVo sysRoleQueryVo);

    void updateStatus(Long id, Integer status);

    void download(HttpServletResponse response);
}
