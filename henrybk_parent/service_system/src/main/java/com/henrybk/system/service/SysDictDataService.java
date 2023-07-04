package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.*;
import com.henrybk.vo.query.sys.SysDictDataQueryVo;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author Henry
 * @since 2023-05-24
 */
public interface SysDictDataService extends IService<SysDictData> {
    List<SysDictData> getTree(SysDictDataQueryVo sysDictDataQueryVo);

}
