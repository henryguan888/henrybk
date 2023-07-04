package com.henrybk.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysDictType;
import com.henrybk.result.R;
import com.henrybk.system.service.SysDictTypeService;
import com.henrybk.vo.query.sys.SysDictTypeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 字典类型表 前端控制器
 * @author Henry
 * @since 2023-05-24
 */
@Api(tags = "系统管理-字典管理")
@ApiSupport(order = 9806)
@RestController
@RequestMapping("/admin/system/sysDictType")
public class SysDictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;


    @ApiOperation(value = "分页查询获取字典类型列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.dictType.query')")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("字典类型查询对象") SysDictTypeQueryVo sysDictTypeQueryVo) {
        Page<SysDictType> page = sysDictTypeService.getDictTypePage(pageNum,pageSize,sysDictTypeQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "添加字典类型")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.dictType.add')")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("字典类型实体") SysDictType sysDictType) {
        sysDictTypeService.saveDictType(sysDictType);
        return R.ok();
    }

    @ApiOperation("根据id查询字典类型")
    @ApiOperationSupport(order = 3)
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('system.dictType.edit')")
    public R getById(@PathVariable @ApiParam("字典类型编号") Long id) {
        SysDictType sysDictType = sysDictTypeService.getById(id);
        return R.ok().data("data",sysDictType);
    }

    @ApiOperation("根据id修改字典类型")
    @ApiOperationSupport(order = 4)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.dictType.edit')")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("字典类型实体") SysDictType sysDictType) {
        sysDictTypeService.updateDictType(sysDictType);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除字典类型")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.dictType.del')")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("字典主键") Long id) {
        sysDictTypeService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除字典类型")
    @ApiOperationSupport(order = 6)
    @DeleteMapping("/batchRemove/{ids}")
    @PreAuthorize("hasAuthority('system.dictType.del')")
    @Log(title = "字典管理", businessType = BusinessTypeEnum.BATCH_DEL)
    public R batchRemoveSysUser(@PathVariable @ApiParam("id集合") List<Long> ids) {
        sysDictTypeService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation(value = "获取所有字典类型")
    @ApiOperationSupport(order = 99)
    @GetMapping("/getDictTypeList")
    public R getDictTypeList() {
        List<SysDictType> list = sysDictTypeService.list();
        return R.ok().data("list",list);
    }
}

