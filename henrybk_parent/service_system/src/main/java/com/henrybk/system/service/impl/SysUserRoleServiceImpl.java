package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.constants.TypeCode;
import com.henrybk.model.sys.SysRole;
import com.henrybk.model.sys.SysUserRole;
import com.henrybk.system.mapper.SysUserRoleMapper;
import com.henrybk.system.service.SysRoleService;
import com.henrybk.system.service.SysUserRoleService;
import com.henrybk.utils.PageUtil;
import com.henrybk.vo.vo.sys.AssignRoleVo;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 用户角色关联表 服务实现类
 * @author Henry
 * @since 2023-05-20
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 获取用户的角色数据
     * @param userId 用户编号
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> toAssign(Long userId) {
        //获取所有角色
        List<SysRole> sysRoles = sysRoleService.list(new QueryWrapper<SysRole>().eq("status", TypeCode.FLAG_ONE));

        //根据用户id查询已经分配的角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> sysUserRoles = baseMapper.selectList(queryWrapper);

        //从sysUserRoles集合里获取所有角色编号
        List<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            userRoleIds.add(sysUserRole.getRoleId());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("sysRoles",sysRoles);
        map.put("userRoleIds",userRoleIds);
        return map;
    }

    /**
     * 根据用户分配角色
     * @param assignRoleVo 分配角色对象
     */
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //根据用户编号删除之前分配的角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",assignRoleVo.getUserId());
        baseMapper.delete(queryWrapper);
        //获取所有角色编号，添加到用户角色关系表中
        List<Long> roleIdList = assignRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
            baseMapper.insert(sysUserRole);
        }
    }

    @Override
    public Page<SysUserVo> getSysUserByRoleId(Long roleId, Integer pageNum, Integer pageSize, SysUserQueryVo sysUserQueryVo) {
        List<SysUserVo> list = baseMapper.getSysUserByRoleId(roleId,sysUserQueryVo);
        return PageUtil.listToPage(list, pageNum, pageSize);
    }

    @Override
    public Page<SysUserVo> getOtherSysUserByRoleId(Long roleId, Integer pageNum, Integer pageSize, SysUserQueryVo sysUserQueryVo) {
        List<SysUserVo> list = baseMapper.getOtherSysUserByRoleId(roleId,sysUserQueryVo);
        return PageUtil.listToPage(list, pageNum, pageSize);
    }

    @Override
    public void saveAssignUser(Long roleId, List<Long> userIds) {
        List<SysUserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            list.add(sysUserRole);
        }
        sysUserRoleService.saveBatch(list);
    }

    @Override
    public void cancelAssignUserById(Long roleId, Long userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("role_id",roleId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void batchCancelAssignUser(Long roleId, List<Long> userIds) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.in("user_id",userIds).eq("role_id",roleId);
        baseMapper.delete(wrapper);
    }


}
