package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.constants.TypeCode;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.model.sys.SysRoleMenu;
import com.henrybk.system.mapper.SysRoleMenuMapper;
import com.henrybk.system.service.SysMenuService;
import com.henrybk.system.service.SysRoleMenuService;
import com.henrybk.utils.TreeUtil;
import com.henrybk.vo.vo.sys.AssignMenuVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author Henry
 * @since 2023-05-21
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 获取角色的菜单树
     * @param roleId 角色编号
     * @return List<SysMenu>
     */
    @Override
    public List<SysMenu> toAssignMenu(Long roleId) {
        //获取所有状态有效的菜单数据
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status", TypeCode.FLAG_ONE);
        List<SysMenu> sysMenus = sysMenuService.list();

        //获取该角色下菜单编号
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<SysRoleMenu> sysRoleMenus = baseMapper.selectList(queryWrapper);

        List<Long> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            roleMenuIds.add(sysRoleMenu.getMenuId());
        }

        //遍历菜单数据，将用户菜单编号的是否选中设置为true
        for (SysMenu sysMenu : sysMenus) {
            sysMenu.setSelect(roleMenuIds.contains(sysMenu.getId()));
        }

        //递归生成菜单树
        return TreeUtil.makeMenuTree(sysMenus, 0L);
    }

    /**
     * 根据角色分配菜单
     * @param assignMenuVo 分配菜单对象
     */
    @Override
    public void doAssignMenu(AssignMenuVo assignMenuVo) {
        //根据角色编号删除角色菜单信息
        baseMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assignMenuVo.getRoleId()));
        //插入角色菜单数据
        List<Long> menuIdList = assignMenuVo.getMenuIdList();
        for (Long menuId : menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            baseMapper.insert(sysRoleMenu);
        }

    }


}
