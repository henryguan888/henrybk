package com.henrybk.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henrybk.model.sys.SysDept;

import java.util.HashMap;

/**
 * @description 组织架构 Mapper 接口
 * @author Henry
 * @since 2023-05-20
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    HashMap<String,Object> getDeptNameByUserName(String username);
}
