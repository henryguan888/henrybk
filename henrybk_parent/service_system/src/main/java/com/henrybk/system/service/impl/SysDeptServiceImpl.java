package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.model.sys.SysDept;
import com.henrybk.system.mapper.SysDeptMapper;
import com.henrybk.system.service.SysDeptService;
import com.henrybk.utils.TreeUtil;
import com.henrybk.vo.query.sys.SysDeptQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description 组织架构 服务实现类
 * @author Henry
 * @since 2023-05-20
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public List<SysDept> getTree(SysDeptQueryVo sysDeptQueryVo) {
        //获取所有部门
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysDeptQueryVo.getName()), SysDept::getName, sysDeptQueryVo.getName());
        queryWrapper.eq(sysDeptQueryVo.getStatus() != null, SysDept::getStatus, sysDeptQueryVo.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(sysDeptQueryVo.getCreateTimeBegin()), SysDept::getCreateTime, sysDeptQueryVo.getCreateTimeBegin());
        queryWrapper.le(StringUtils.isNotEmpty(sysDeptQueryVo.getCreateTimeEnd()), SysDept::getCreateTime, sysDeptQueryVo.getCreateTimeEnd());
        queryWrapper.orderByAsc(SysDept::getParentId);
        queryWrapper.orderByAsc(SysDept::getOrderNum);
        List<SysDept> sysDeptList = baseMapper.selectList(queryWrapper);

        //递归生成部门树信息
        return TreeUtil.makeDeptTree(sysDeptList,0L);
    }

    @Override
    public List<Long> getDeptIds(Long deptId) {
        List<Long> list = new ArrayList<>();

        // 将传入id加入到list
        list.add(deptId);
        // 获取所有部门列表
        List<SysDept> sysDeptList = baseMapper.selectList(null);
        // 递归获取子部门id
        Optional.ofNullable(sysDeptList).orElse(new ArrayList<>()).stream()
                .filter(item -> item != null && item.getParentId().equals(deptId))
                .forEach(item -> {
                    List<Long> ids = getDeptIds(item.getId());
                    list.addAll(ids);
                });
        return list;
    }

}
