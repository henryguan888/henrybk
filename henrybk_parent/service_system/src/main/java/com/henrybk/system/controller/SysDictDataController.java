package com.henrybk.system.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.constants.TypeCode;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysDictData;
import com.henrybk.result.R;
import com.henrybk.system.service.SysDictDataService;
import com.henrybk.vo.query.sys.SysDictDataQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 字典数据表 前端控制器
 * @author Henry
 * @since 2023-05-24
 */
@Api(tags = "系统管理-字典数据")
@ApiSupport(order = 9807)
@RestController
@RequestMapping("/admin/system/sysDictData")
public class SysDictDataController {

    @Resource
    private SysDictDataService sysDictDataService;

    @ApiOperation(value = "获取字典数据树")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getTree")
    @Cacheable(value = TypeCode.DATA_DICT_TYPE_KEY)
    @PreAuthorize("hasAuthority('system.dictData.query')")
    public R getTree(SysDictDataQueryVo sysDictDataQueryVo) {
        List<SysDictData> list = sysDictDataService.getTree(sysDictDataQueryVo);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "添加字典数据")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @CacheEvict(value = TypeCode.DATA_DICT_TYPE_KEY,allEntries = true)
    @PreAuthorize("hasAuthority('system.dictData.add')")
    @Log(title = "字典数据", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("用户信息实体") SysDictData sysDictData) {
        sysDictDataService.save(sysDictData);
        return R.ok();
    }

    @ApiOperation("根据id查询字典数据")
    @ApiOperationSupport(order = 3)
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('system.dictData.edit')")
    public R getById(@PathVariable @ApiParam("用户编号") Long id) {
        SysDictData sysDictType = sysDictDataService.getById(id);
        return R.ok().data("data",sysDictType);
    }

    @ApiOperation("根据id修改字典数据")
    @ApiOperationSupport(order = 4)
    @PutMapping("/updateById")
    @CacheEvict(value = TypeCode.DATA_DICT_TYPE_KEY,allEntries = true)
    @PreAuthorize("hasAuthority('system.dictData.edit')")
    @Log(title = "字典数据", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("用户信息实体") SysDictData sysDictData) {
        sysDictDataService.updateById(sysDictData);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除字典数据")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/removeById/{id}")
    @CacheEvict(value = TypeCode.DATA_DICT_TYPE_KEY,allEntries = true)
    @PreAuthorize("hasAuthority('system.dictData.del')")
    @Log(title = "字典数据", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("字典主键") Long id) {
        sysDictDataService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除字典数据")
    @ApiOperationSupport(order = 6)
    @DeleteMapping("/batchRemove/{ids}")
    @CacheEvict(value = TypeCode.DATA_DICT_TYPE_KEY,allEntries = true)
    @PreAuthorize("hasAuthority('system.dictData.del')")
    @Log(title = "字典数据", businessType = BusinessTypeEnum.BATCH_DEL)
    public R batchRemoveSysUser(@PathVariable @ApiParam("id集合") List<Long> ids) {
        sysDictDataService.removeByIds(ids);
        return R.ok();
    }


}

