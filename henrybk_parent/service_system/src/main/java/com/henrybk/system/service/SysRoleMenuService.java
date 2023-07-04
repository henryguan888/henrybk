package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.model.sys.SysRoleMenu;
import com.henrybk.vo.vo.sys.AssignMenuVo;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author Henry
 * @since 2023-05-21
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<SysMenu> toAssignMenu(Long roleId);

    void doAssignMenu(AssignMenuVo assignMenuVo);

}
