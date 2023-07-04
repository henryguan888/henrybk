package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.enums.ResultEnum;
import com.henrybk.enums.StatusEnum;
import com.henrybk.exception.BusinessException;
import com.henrybk.model.sys.SysDictType;
import com.henrybk.system.mapper.SysDictTypeMapper;
import com.henrybk.system.service.SysDictTypeService;
import com.henrybk.utils.EnumUtil;
import com.henrybk.vo.query.sys.SysDictTypeQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author Henry
 * @since 2023-05-24
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public Page<SysDictType> getDictTypePage(Integer pageNum, Integer pageSize, SysDictTypeQueryVo sysDictTypeQueryVo) {
        Page<SysDictType> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysDictTypeQueryVo.getDictType()),SysDictType::getDictType,sysDictTypeQueryVo.getDictType())
                .like(StringUtils.isNotEmpty(sysDictTypeQueryVo.getDictName()),SysDictType::getDictName,sysDictTypeQueryVo.getDictName())
                .eq(sysDictTypeQueryVo.getStatus() != null,SysDictType::getStatus,sysDictTypeQueryVo.getStatus())
                .ge(StringUtils.isNotEmpty(sysDictTypeQueryVo.getCreateTimeBegin()), SysDictType::getCreateTime,sysDictTypeQueryVo.getCreateTimeBegin())
                .le(StringUtils.isNotEmpty(sysDictTypeQueryVo.getCreateTimeEnd()),SysDictType::getCreateTime,sysDictTypeQueryVo.getCreateTimeEnd())
                .orderByAsc(SysDictType::getId);
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void saveDictType(SysDictType sysDictType) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictType::getDictType,sysDictType.getDictType());
        SysDictType selectOne = baseMapper.selectOne(queryWrapper);
        if (selectOne != null && selectOne.getId().intValue() != sysDictType.getId()) {
            throw new BusinessException(ResultEnum.DICT_TYPE_EXIST);
        }
        baseMapper.insert(sysDictType);
    }

    @Override
    public void updateDictType(SysDictType sysDictType) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictType::getDictType,sysDictType.getDictType());
        SysDictType selectOne = baseMapper.selectOne(queryWrapper);
        if (selectOne.getId().intValue() != sysDictType.getId()) {
            throw new BusinessException(ResultEnum.DICT_TYPE_EXIST);
        }
        baseMapper.updateById(sysDictType);
    }

}
