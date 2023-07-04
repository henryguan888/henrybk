package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.model.sys.SysDictData;
import com.henrybk.system.mapper.SysDictDataMapper;
import com.henrybk.system.service.SysDictDataService;
import com.henrybk.vo.query.sys.SysDictDataQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author Henry
 * @since 2023-05-24
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public List<SysDictData> getTree(SysDictDataQueryVo sysDictDataQueryVo) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(sysDictDataQueryVo.getId() != null, SysDictData::getParentId, sysDictDataQueryVo.getId());
        queryWrapper.eq(StringUtils.isNotEmpty(sysDictDataQueryVo.getDictType()), SysDictData::getDictType, sysDictDataQueryVo.getDictType());
        queryWrapper.like(StringUtils.isNotEmpty(sysDictDataQueryVo.getDictLabel()), SysDictData::getDictLabel, sysDictDataQueryVo.getDictLabel());
        queryWrapper.eq(sysDictDataQueryVo.getStatus() != null, SysDictData::getStatus, sysDictDataQueryVo.getStatus());
        queryWrapper.ge(StringUtils.isNotEmpty(sysDictDataQueryVo.getCreateTimeBegin()), SysDictData::getCreateTime, sysDictDataQueryVo.getCreateTimeBegin());
        queryWrapper.le(StringUtils.isNotEmpty(sysDictDataQueryVo.getCreateTimeEnd()), SysDictData::getCreateTime, sysDictDataQueryVo.getCreateTimeEnd());
        queryWrapper.orderByAsc(SysDictData::getDictType);
        queryWrapper.orderByAsc(SysDictData::getOrderNum);
        List<SysDictData> dictDataList = baseMapper.selectList(queryWrapper);
        //向list集合每个dict对象中设置hasChildren
        for (SysDictData dict:dictDataList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictDataList;
    }

    private boolean isChildren(Long id) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getParentId,id);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }


}
