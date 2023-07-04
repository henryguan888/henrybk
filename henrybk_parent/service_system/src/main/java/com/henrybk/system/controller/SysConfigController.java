package com.henrybk.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysConfig;
import com.henrybk.result.R;
import com.henrybk.system.service.SysConfigService;
import com.henrybk.vo.query.sys.SysConfigQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 系统配置表 前端控制器
 * @author Henry
 * @since 2023-05-22
 */
@Api(tags = "系统管理-参数设置")
@ApiSupport(order = 9808)
@RestController
@RequestMapping("/admin/system/sysConfig")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    @ApiOperation(value = "分页查询系统配置列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.config.query')")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("系统配置查询对象") SysConfigQueryVo sysConfigQueryVo) {
        Page<SysConfig> page = sysConfigService.getSysConfigPage(pageNum,pageSize,sysConfigQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "添加系统配置信息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.config.add')")
    @Log(title = "配置管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("系统配置实体") SysConfig sysConfig) {
        sysConfigService.save(sysConfig);
        return R.ok();
    }

    @ApiOperation("根据id查询系统配置信息")
    @ApiOperationSupport(order = 2)
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('system.config.edit')")
    public R getById(@PathVariable @ApiParam("配置编号") Long id) {
        SysConfig sysConfig = sysConfigService.getById(id);
        return R.ok().data("data",sysConfig);
    }

    @ApiOperation("根据id修改系统配置信息")
    @ApiOperationSupport(order = 3)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.config.edit')")
    @Log(title = "配置管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("系统配置实体") SysConfig sysConfig) {
        sysConfigService.updateById(sysConfig);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除系统配置信息")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.config.del')")
    @Log(title = "配置管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("配置编号") Long id) {
        sysConfigService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除系统配置信息")
    @ApiOperationSupport(order = 6)
    @DeleteMapping("/batchRemove/{ids}")
    @PreAuthorize("hasAuthority('system.config.del')")
    @Log(title = "配置管理", businessType = BusinessTypeEnum.DELETE)
    public R batchRemove(@PathVariable @ApiParam("id集合") List<Long> ids) {
        sysConfigService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation(value = "根据配置类键获取配置集合")
    @ApiOperationSupport(order = 99)
    @GetMapping("/getConfigList/{configKey}")
    public R getConfigList(@PathVariable String configKey) {
        List<Object> list = sysConfigService.getSysConfigList(configKey);
        return R.ok().data("list",list);
    }
}

