package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.model.sys.SysPost;
import com.henrybk.system.mapper.SysPostMapper;
import com.henrybk.system.service.SysPostService;
import com.henrybk.utils.EnumUtil;
import com.henrybk.utils.ExcelUtil;
import com.henrybk.vo.easyexcel.SysPostEeVo;
import com.henrybk.vo.query.sys.SysPostQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 岗位信息表 服务实现类
 * @author Henry
 * @since 2023-05-20
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private SysPostQueryVo sysPostQueryVo;

    @Override
    public Page<SysPost> getSysPostPage(Integer pageNum, Integer pageSize, SysPostQueryVo sysPostQueryVo) {
        this.sysPostQueryVo = sysPostQueryVo;
        Page<SysPost> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysPostQueryVo.getPostCode()), SysPost::getPostCode, sysPostQueryVo.getPostCode());
        queryWrapper.like(StringUtils.isNotEmpty(sysPostQueryVo.getName()), SysPost::getName, sysPostQueryVo.getName());
        queryWrapper.eq(sysPostQueryVo.getStatus() != null, SysPost::getStatus, sysPostQueryVo.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(sysPostQueryVo.getCreateTimeBegin()), SysPost::getCreateTime, sysPostQueryVo.getCreateTimeBegin());
        queryWrapper.le(StringUtils.isNotEmpty(sysPostQueryVo.getCreateTimeEnd()), SysPost::getCreateTime, sysPostQueryVo.getCreateTimeEnd());
        queryWrapper.orderByAsc(SysPost::getOrderNum);

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysPost sysPost = baseMapper.selectById(id);
        sysPost.setStatus(EnumUtil.getEnumByCode(status));
        baseMapper.updateById(sysPost);
    }

    @Override
    public void download(HttpServletResponse response) {
        if (this.sysPostQueryVo == null) {
            return;
        }
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(this.sysPostQueryVo.getPostCode()),"post_code",this.sysPostQueryVo.getPostCode())
                .like(StringUtils.isNotEmpty(this.sysPostQueryVo.getName()),"name",this.sysPostQueryVo.getName())
                .eq(this.sysPostQueryVo.getStatus() != null,"status",this.sysPostQueryVo.getStatus());
        List<SysPost> sysPostList = baseMapper.selectList(queryWrapper);
        List<SysPostEeVo> SysPostEeVoList = new ArrayList<>();
        for (SysPost sysPost : sysPostList) {
            SysPostEeVo sysPostEeVo = new SysPostEeVo();
            BeanUtils.copyProperties(sysPost, sysPostEeVo);
            sysPostEeVo.setStatus(sysPost.getStatus().getName());
            SysPostEeVoList.add(sysPostEeVo);
        }
        ExcelUtil.export(response,"岗位列表",null,SysPostEeVoList, SysPostEeVo.class);
    }
}
