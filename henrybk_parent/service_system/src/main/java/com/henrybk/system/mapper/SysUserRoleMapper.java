package com.henrybk.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henrybk.model.sys.SysUserRole;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 用户角色关联表 Mapper 接口
 * @author Henry
 * @since 2023-05-20
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<SysUserVo> getSysUserByRoleId(@Param("roleId") Long roleId, @Param("sysUserQueryVo") SysUserQueryVo sysUserQueryVo);

    List<SysUserVo> getOtherSysUserByRoleId(@Param("roleId") Long roleId, @Param("sysUserQueryVo") SysUserQueryVo sysUserQueryVo);
}
