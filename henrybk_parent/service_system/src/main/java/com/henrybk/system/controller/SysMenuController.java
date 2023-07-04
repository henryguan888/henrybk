package com.henrybk.system.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.result.R;
import com.henrybk.system.service.SysMenuService;
import com.henrybk.vo.query.sys.SysMenuQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 菜单权限 前端控制器
 * @author Henry
 * @since 2023-05-18
 */
@Api(tags = "系统管理-菜单管理")
@ApiSupport(order = 9803)
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单树")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getTree")
    @PreAuthorize("hasAuthority('system.menu.query')")
    public R getTree(SysMenuQueryVo sysMenuQueryVo) {
        List<SysMenu> list = sysMenuService.getMenuList(sysMenuQueryVo);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "添加菜单信息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.menu.add')")
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("菜单信息实体") SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return R.ok();
    }

    @ApiOperation("根据id修改菜单信息")
    @ApiOperationSupport(order = 3)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.menu.edit')")
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("菜单信息实体") SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除菜单信息")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.menu.del')")
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("菜单编号") Long id) {
        sysMenuService.removeById(id);
        return R.ok();
    }


    @ApiOperation(value = "根据枚举类名获取枚举集合")
    @ApiOperationSupport(order = 499)
    @GetMapping("/getModuleList")
    public R getModuleList() {
        List<SysMenu> list = sysMenuService.getModuleList();
        return R.ok().data("list",list);
    }

}

