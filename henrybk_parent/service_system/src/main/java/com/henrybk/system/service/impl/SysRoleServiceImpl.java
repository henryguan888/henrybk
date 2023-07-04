package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.sys.SysRole;
import com.henrybk.system.mapper.SysRoleMapper;
import com.henrybk.system.service.SysRoleService;
import com.henrybk.utils.EnumUtil;
import com.henrybk.utils.ExcelUtil;
import com.henrybk.vo.easyexcel.SysRoleEeVo;
import com.henrybk.vo.query.sys.SysRoleQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 角色信息表 服务实现类
 * @author Henry
 * @since 2023-05-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private SysRoleQueryVo sysRoleQueryVo;

    @Override
    public Page<SysRole> getSysRolePage(Integer pageNum, Integer pageSize, SysRoleQueryVo sysRoleQueryVo) {
        this.sysRoleQueryVo = sysRoleQueryVo;
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysRoleQueryVo.getRoleName()), SysRole::getRoleName, sysRoleQueryVo.getRoleName());
        queryWrapper.like(StringUtils.isNotEmpty(sysRoleQueryVo.getRoleCode()), SysRole::getRoleCode, sysRoleQueryVo.getRoleCode());
        queryWrapper.eq(sysRoleQueryVo.getStatus() != null, SysRole::getStatus, sysRoleQueryVo.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(sysRoleQueryVo.getCreateTimeBegin()), SysRole::getCreateTime, sysRoleQueryVo.getCreateTimeBegin());
        queryWrapper.le(StringUtils.isNotEmpty(sysRoleQueryVo.getCreateTimeEnd()), SysRole::getCreateTime, sysRoleQueryVo.getCreateTimeEnd());
        queryWrapper.orderByAsc(SysRole::getOrderNum);
        return baseMapper.selectPage(page, queryWrapper);

    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysRole sysRole = baseMapper.selectById(id);
        sysRole.setStatus(EnumUtil.getEnumByCode(StatusEnum.class,status));
        baseMapper.updateById(sysRole);
    }

    @Override
    public void download(HttpServletResponse response) {
        if (this.sysRoleQueryVo == null) {
            return;
        }
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(this.sysRoleQueryVo.getRoleName()), SysRole::getRoleName, this.sysRoleQueryVo.getRoleName())
                .like(StringUtils.isNotEmpty(this.sysRoleQueryVo.getRoleCode()), SysRole::getRoleCode, this.sysRoleQueryVo.getRoleCode())
                .ge(StringUtils.isNotEmpty(this.sysRoleQueryVo.getCreateTimeBegin()), SysRole::getCreateTime, this.sysRoleQueryVo.getCreateTimeBegin())
                .le(StringUtils.isNotEmpty(this.sysRoleQueryVo.getCreateTimeEnd()), SysRole::getCreateTime, this.sysRoleQueryVo.getCreateTimeEnd())
                .orderByAsc(SysRole::getOrderNum);
        List<SysRole> sysRoleList = baseMapper.selectList(queryWrapper);
        List<SysRoleEeVo> sysRoleEeVoList = new ArrayList<>();
        for (SysRole sysRole : sysRoleList) {
            SysRoleEeVo sysRoleEeVo = new SysRoleEeVo();
            BeanUtils.copyProperties(sysRole, sysRoleEeVo);
            sysRoleEeVo.setStatus(sysRole.getStatus().getName());
            sysRoleEeVoList.add(sysRoleEeVo);
        }
        ExcelUtil.export(response,"角色列表",null,sysRoleEeVoList,SysRoleEeVo.class);
    }
}
