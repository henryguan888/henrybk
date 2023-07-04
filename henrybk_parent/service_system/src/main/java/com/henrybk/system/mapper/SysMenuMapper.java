package com.henrybk.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henrybk.model.sys.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 菜单权限表 Mapper 接口
 * @author Henry
 * @since 2023-05-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getMenuListByUserId(Long userId);

    SysMenu selectLastMenu(@Param("menuType") Integer menuType, @Param("parentId") Long parentId);
}
