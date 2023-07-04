package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysDictType;
import com.henrybk.vo.query.sys.SysDictTypeQueryVo;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author Henry
 * @since 2023-05-24
 */
public interface SysDictTypeService extends IService<SysDictType> {

    Page<SysDictType> getDictTypePage(Integer pageNum, Integer pageSize, SysDictTypeQueryVo sysDictTypeQueryVo);

    void saveDictType(SysDictType sysDictType);

    void updateDictType(SysDictType sysDictType);


}
