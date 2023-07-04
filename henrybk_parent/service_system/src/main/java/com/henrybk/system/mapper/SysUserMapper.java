package com.henrybk.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henrybk.model.sys.SysUser;
import com.henrybk.vo.easyexcel.SysUserEeVo;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.vo.sys.DateCountVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description 用户信息表 Mapper 接口
 * @author Henry
 * @since 2023-05-15
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUserVo> selectPage(Page<SysUserVo> page, @Param("vo") SysUserQueryVo sysUserQueryVo);

    List<SysUserEeVo> selectUserVoList(@Param("vo") SysUserQueryVo sysUserQueryVo);

    SysUserVo getUserInfo(Long userId);

    Map<String, Object> getSystemData();

    List<Map<String, Object>> getUserByRole();

    List<DateCountVo> getLoginStatistics(String searchDate);

    List<DateCountVo> getOperStatistics(String searchDate);

    Long selectMainConsoleCount(Long userId);
}
