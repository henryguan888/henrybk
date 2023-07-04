package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.constants.TypeCode;
import com.henrybk.model.sys.SysConfig;
import com.henrybk.system.mapper.SysConfigMapper;
import com.henrybk.system.service.SysConfigService;
import com.henrybk.vo.query.sys.SysConfigQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description 系统配置表 服务实现类
 * @author Henry
 * @since 2023-05-22
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {


    @Override
    public Page<SysConfig> getSysConfigPage(Integer pageNum, Integer pageSize, SysConfigQueryVo sysConfigQueryVo) {
        Page<SysConfig> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysConfigQueryVo.getConfigKey()),SysConfig::getConfigKey,sysConfigQueryVo.getConfigKey());
        queryWrapper.like(StringUtils.isNotEmpty(sysConfigQueryVo.getConfigName()),SysConfig::getConfigName,sysConfigQueryVo.getConfigName());
        queryWrapper.ge(StringUtils.isNotEmpty(sysConfigQueryVo.getCreateTimeBegin()), SysConfig::getCreateTime, sysConfigQueryVo.getCreateTimeBegin());
        queryWrapper.le(StringUtils.isNotEmpty(sysConfigQueryVo.getCreateTimeEnd()), SysConfig::getCreateTime, sysConfigQueryVo.getCreateTimeEnd());
        queryWrapper.orderByDesc(SysConfig::getCreateTime);
        return baseMapper.selectPage(page,queryWrapper);
    }

    /**
     * 通过key获取value，返回字符串
     * @param configKey 键
     * @return ConfigValue 值
     */
    @Override
    public String getSysConfig(String configKey) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getConfigKey,configKey);
        SysConfig sysConfig = baseMapper.selectOne(queryWrapper);
        return sysConfig.getConfigValue();
    }

    /**
     * 通过多个key返回Map<K,V>
     * @param configKeys 多个键
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getSysConfigMap(String... configKeys) {
        Map<String,Object> map = new HashMap<>();
        for (String configKey : configKeys) {
            map.put(configKey,this.getSysConfig(configKey));
        }
        return map;
    }

    /**
     * 通过key将value根据;分割，返回list
     * @param configKey 键
     * @return List<Object>
     */
    @Override
    public List<Object> getSysConfigList(String configKey) {
        String sysConfigs = this.getSysConfig(configKey);
        String[] arr = sysConfigs.split(";");
        List<Object> list = new ArrayList<>(arr.length);
        Collections.addAll(list, arr);
        return list;
    }

    @Override
    public boolean isSuperAdmin(String username) {
        List<Object> sysConfigList = getSysConfigList(TypeCode.SUPER_ADMIN);
        return sysConfigList.contains(username);
    }
}
