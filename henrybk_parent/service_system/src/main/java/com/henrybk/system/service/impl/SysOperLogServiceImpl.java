package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.model.sys.SysDept;
import com.henrybk.model.sys.SysOperLog;
import com.henrybk.model.sys.SysUser;
import com.henrybk.service.AsyncOperLogService;
import com.henrybk.system.mapper.SysOperLogMapper;
import com.henrybk.system.service.SysDeptService;
import com.henrybk.system.service.SysOperLogService;
import com.henrybk.system.service.SysUserService;
import com.henrybk.vo.query.sys.SysOperLogQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description 操作日志记录 服务实现类
 * @author Henry
 * @since 2023-05-26
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService, AsyncOperLogService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysDeptService sysDeptService;

    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        SysUser sysUser = sysUserService.getByUsername(sysOperLog.getOperName());
        SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());

        if (sysUser != null) {
            sysOperLog.setOperName(sysUser.getUsername());
        }
        if (sysDept != null) {
            sysOperLog.setDeptName(sysDept.getName());
        }
        baseMapper.insert(sysOperLog);
    }

    @Override
    public Page<SysOperLog> getPageList(Integer pageNum, Integer pageSize, SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysOperLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(sysOperLogQueryVo.getTitle()),SysOperLog::getTitle,sysOperLogQueryVo.getTitle());
        queryWrapper.eq(sysOperLogQueryVo.getBusinessType() != null,SysOperLog::getBusinessType,sysOperLogQueryVo.getBusinessType());
        queryWrapper.like(StringUtils.isNotEmpty(sysOperLogQueryVo.getOperName()),SysOperLog::getOperName,sysOperLogQueryVo.getOperName());
        queryWrapper.gt(StringUtils.isNotEmpty(sysOperLogQueryVo.getCreateTimeBegin()), SysOperLog::getOperTime, sysOperLogQueryVo.getCreateTimeBegin());
        queryWrapper.lt(StringUtils.isNotEmpty(sysOperLogQueryVo.getCreateTimeEnd()), SysOperLog::getOperTime, sysOperLogQueryVo.getCreateTimeEnd());
        queryWrapper.orderByDesc(SysOperLog::getOperTime);
        return baseMapper.selectPage(page,queryWrapper);
    }
}
