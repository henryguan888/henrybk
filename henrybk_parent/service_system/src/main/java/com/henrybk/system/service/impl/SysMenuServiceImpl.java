package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.constants.TypeCode;
import com.henrybk.enums.MenuTypeEnum;
import com.henrybk.enums.ResultEnum;
import com.henrybk.exception.BusinessException;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.system.mapper.SysMenuMapper;
import com.henrybk.system.service.SysConfigService;
import com.henrybk.system.service.SysMenuService;
import com.henrybk.system.service.SysUserService;
import com.henrybk.utils.TreeUtil;
import com.henrybk.vo.query.sys.SysMenuQueryVo;
import com.henrybk.vo.vo.sys.RouterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 菜单权限表 服务实现类
 * @author Henry
 * @since 2023-05-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysConfigService sysConfigService;


    @Override
    public List<SysMenu> getMenuList(SysMenuQueryVo sysMenuQueryVo) {
        //获取所有菜单
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysMenuQueryVo.getMenuName()), SysMenu::getMenuName, sysMenuQueryVo.getMenuName());
        queryWrapper.eq(sysMenuQueryVo.getStatus() != null, SysMenu::getStatus, sysMenuQueryVo.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(sysMenuQueryVo.getCreateTimeBegin()), SysMenu::getCreateTime, sysMenuQueryVo.getCreateTimeBegin());
        queryWrapper.le(StringUtils.isNotEmpty(sysMenuQueryVo.getCreateTimeEnd()), SysMenu::getCreateTime, sysMenuQueryVo.getCreateTimeEnd());
        queryWrapper.orderByAsc(SysMenu::getParentId);
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> sysMenuList = baseMapper.selectList(queryWrapper);

        //递归获取
        return TreeUtil.makeMenuTree(sysMenuList, 0L);
    }

    @Override
    public void saveMenu(SysMenu sysMenu) {
        if (sysMenu.getParentId() == null) {
            sysMenu.setParentId(0L);
        }

        // 获取当前同菜单类型的最大Id
        SysMenu menu = baseMapper.selectLastMenu(sysMenu.getMenuType().getCode(),sysMenu.getParentId());

        if (menu != null) {
            sysMenu.setId(menu.getId() + 1L);
        } else {
            sysMenu.setId(sysMenu.getParentId() * 100L + 1L);
        }

        if (baseMapper.selectById(sysMenu.getId()) != null) {
            throw new BusinessException(ResultEnum.PRIMARY_KEY_CONFLICT);
        }
        baseMapper.insert(sysMenu);
    }

    @Override
    public List<SysMenu> getModuleList() {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuType, MenuTypeEnum.MENU.getCode());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<RouterVo> getUserMenuList(Long userId) {
        List<SysMenu> list;
        //超级管理员，查询所有权限数据
        if (sysConfigService.isSuperAdmin(sysUserService.getById(userId).getUsername())) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getStatus, TypeCode.FLAG_ONE).notIn(SysMenu::getId,10L,1001L)
                    .orderByAsc(SysMenu::getParentId).orderByAsc(SysMenu::getOrderNum);
            list = baseMapper.selectList(queryWrapper);
        } else {
            list = baseMapper.getMenuListByUserId(userId);
        }
        //生成菜单树
        list = TreeUtil.makeMenuTree(list, 0L);
        //将菜单树转换成路由树
        return TreeUtil.makeRouter(list, 0L);
    }

    @Override
    public List<String> getUserButtonList(Long userId) {
        List<SysMenu> sysMenuList;
        //超级管理员，查询所有按钮数据
        if (sysConfigService.isSuperAdmin(sysUserService.getById(userId).getUsername())) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getStatus, TypeCode.FLAG_ONE);
            sysMenuList = baseMapper.selectList(queryWrapper);
        } else {
            sysMenuList = baseMapper.getMenuListByUserId(userId);
        }
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getMenuType().equals(MenuTypeEnum.BUTTON))
                permissionList.add(sysMenu.getPerms());
        }
        return permissionList;
    }


}
