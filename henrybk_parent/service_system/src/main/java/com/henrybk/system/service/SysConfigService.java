package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysConfig;
import com.henrybk.vo.query.sys.SysConfigQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @description 系统配置表 服务类
 * @author Henry
 * @since 2023-05-22
 */
public interface SysConfigService extends IService<SysConfig> {

    Page<SysConfig> getSysConfigPage(Integer pageNum, Integer pageSize, SysConfigQueryVo sysConfigQueryVo);

    String getSysConfig(String configKey);

    Map<String,Object> getSysConfigMap(String ...configKey);

    List<Object> getSysConfigList(String configKeys);

    boolean isSuperAdmin(String username);

}
