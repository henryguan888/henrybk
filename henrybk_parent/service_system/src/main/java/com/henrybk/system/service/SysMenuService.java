package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.vo.vo.sys.RouterVo;
import com.henrybk.vo.query.sys.SysMenuQueryVo;

import java.util.List;

/**
 * @description 菜单权限表 服务类
 * @author Henry
 * @since 2023-05-18
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getMenuList(SysMenuQueryVo sysMenuQueryVo);

    List<RouterVo> getUserMenuList(Long userId);

    List<String> getUserButtonList(Long userId);

    void saveMenu(SysMenu sysMenu);

    List<SysMenu> getModuleList();
}
