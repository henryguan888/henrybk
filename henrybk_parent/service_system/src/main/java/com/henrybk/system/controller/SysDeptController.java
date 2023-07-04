package com.henrybk.system.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysDept;
import com.henrybk.result.R;
import com.henrybk.system.service.SysDeptService;
import com.henrybk.vo.query.sys.SysDeptQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 组织架构 前端控制器
 * @author Henry
 * @since 2023-05-20
 */
@Api(tags = "系统管理-部门管理")
@ApiSupport(order = 9804)
@RestController
@RequestMapping("/admin/system/sysDept")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @ApiOperation(value = "获取部门树")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getTree")
    @PreAuthorize("hasAuthority('system.dept.query')")
    public R getTree(@ApiParam("部门查询对象")SysDeptQueryVo sysDeptQueryVo) {
        List<SysDept> list = sysDeptService.getTree(sysDeptQueryVo);
        return R.ok().data("list",list);
    }
    @ApiOperation(value = "添加部门信息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.dept.add')")
    @Log(title = "部门管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("部门信息实体") SysDept sysDept) {
        sysDeptService.save(sysDept);
        return R.ok();
    }

    @ApiOperation("根据id修改部门信息")
    @ApiOperationSupport(order = 3)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.dept.edit')")
    @Log(title = "部门管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("部门信息实体") SysDept sysDept) {
        sysDeptService.updateById(sysDept);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除部门信息")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.dept.del')")
    @Log(title = "部门管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("部门编号") Long id) {
        sysDeptService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "获取所有部门信息")
    @ApiOperationSupport(order = 99)
    @GetMapping("/getDeptList")
    public R getDeptList() {
        List<SysDept> list = sysDeptService.list();
        return R.ok().data("list",list);
    }
}

