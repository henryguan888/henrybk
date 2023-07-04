package com.henrybk.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysMenu;
import com.henrybk.model.sys.SysRole;
import com.henrybk.result.R;
import com.henrybk.system.service.SysRoleMenuService;
import com.henrybk.system.service.SysRoleService;
import com.henrybk.system.service.SysUserRoleService;
import com.henrybk.vo.query.sys.SysRoleQueryVo;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.vo.sys.AssignMenuVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description 角色信息 前端控制器
 * @author Henry
 * @since 2023-05-18
 */
@Api(tags = "系统管理-角色管理")
@ApiSupport(order = 9802)
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "分页查询角色信息列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.role.query')")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("角色查询对象") SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> page = sysRoleService.getSysRolePage(pageNum,pageSize,sysRoleQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "添加角色信息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.role.add')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("角色信息实体") SysRole sysRole) {
        sysRoleService.save(sysRole);
        return R.ok();
    }

    @ApiOperation("根据id查询角色信息")
    @ApiOperationSupport(order = 3)
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('system.role.edit')")
    public R getById(@PathVariable @ApiParam("角色编号") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return R.ok().data("data",sysRole);
    }

    @ApiOperation("根据id修改角色信息")
    @ApiOperationSupport(order = 4)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.role.edit')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("角色信息实体") SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return R.ok();
    }

    @ApiOperation(value = "根据id更新角色状态")
    @ApiOperationSupport(order = 5)
    @GetMapping("/updateStatus/{id}/{status}")
    @PreAuthorize("hasAuthority('system.role.edit')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.UPDATE_STATUS)
    public R updateStatus(@PathVariable @ApiParam("岗位编号") Long id,
                          @PathVariable @ApiParam("岗位状态（0停用 1正常）") Integer status) {
        sysRoleService.updateStatus(id, status);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除角色信息")
    @ApiOperationSupport(order = 6)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.role.del')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("岗位编号") Long id) {
        sysRoleService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除角色信息")
    @ApiOperationSupport(order = 7)
    @DeleteMapping("/batchRemove/{ids}")
    @PreAuthorize("hasAuthority('system.role.del')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.DELETE)
    public R batchRemove(@PathVariable @ApiParam("id集合") List<Long> ids) {
        sysRoleService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation(value="导出角色信息")
    @ApiOperationSupport(order = 8)
    @GetMapping(value = "/download")
    @PreAuthorize("hasAuthority('system.role.download')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.DOWNLOAD)
    public void download(HttpServletResponse response) {
        sysRoleService.download(response);
    }

    @ApiOperation(value = "获取角色的菜单树")
    @ApiOperationSupport(order = 9)
    @GetMapping("/toAssignMenu/{roleId}")
    @PreAuthorize("hasAuthority('system.role.assignMenu')")
    public R toAssignMenu(@PathVariable @ApiParam("角色编号") Long roleId) {
        List<SysMenu> list = sysRoleMenuService.toAssignMenu(roleId);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "根据角色分配菜单")
    @ApiOperationSupport(order = 10)
    @PostMapping("/doAssignMenu")
    @PreAuthorize("hasAuthority('system.role.assignMenu')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.ASSIGN)
    public R doAssignMenu(@RequestBody @ApiParam("分配角色菜单") AssignMenuVo assignMenuVo) {
        sysRoleMenuService.doAssignMenu(assignMenuVo);
        return R.ok();
    }

    @ApiOperation(value = "根据角色编号获取用户")
    @ApiOperationSupport(order = 11)
    @GetMapping("/getSysUserByRoleId/{roleId}/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.role.assignUser')")
    public R getSysUserByRoleId(@PathVariable @ApiParam("角色编号") Long roleId,
                                @PathVariable @ApiParam("当前页") Integer pageNum,
                                @PathVariable @ApiParam("每页记录数") Integer pageSize,
                                @ApiParam("用户查询实体") SysUserQueryVo sysUserQueryVo) {
        Page<SysUserVo> page =  sysUserRoleService.getSysUserByRoleId(roleId,pageNum,pageSize,sysUserQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "根据角色编号获取还未授权的用户")
    @ApiOperationSupport(order = 12)
    @GetMapping("/getOtherSysUserByRoleId/{roleId}/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.role.assignUser')")
    public R getOtherSysUserByRoleId(@PathVariable @ApiParam("角色编号") Long roleId,
                                     @PathVariable @ApiParam("当前页") Integer pageNum,
                                     @PathVariable @ApiParam("每页记录数") Integer pageSize,
                                     @ApiParam("用户查询实体") SysUserQueryVo sysUserQueryVo) {
        Page<SysUserVo> page =  sysUserRoleService.getOtherSysUserByRoleId(roleId,pageNum,pageSize,sysUserQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "授权用户")
    @ApiOperationSupport(order = 13)
    @PostMapping("/saveAssignUser/{roleId}/{userIds}")
    @PreAuthorize("hasAuthority('system.role.assignUser')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.ASSIGN)
    public R saveAssignUser(@PathVariable @ApiParam("角色编号") Long roleId,
                            @PathVariable @ApiParam("用户编号集合") List<Long> userIds){
        sysUserRoleService.saveAssignUser(roleId,userIds);
        return R.ok();
    }

    @ApiOperation(value = "取消授权")
    @ApiOperationSupport(order = 14)
    @DeleteMapping("/cancelAssignUserById/{roleId}/{userId}")
    @PreAuthorize("hasAuthority('system.role.assignUser')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.CANCEL_ASSIGN)
    public R cancelAssignUserById(@PathVariable @ApiParam("角色编号") Long roleId,
                                  @PathVariable @ApiParam("用户编号") Long userId) {
        sysUserRoleService.cancelAssignUserById(roleId,userId);
        return R.ok();
    }

    @ApiOperation(value = "批量取消授权")
    @ApiOperationSupport(order = 15)
    @DeleteMapping("/batchCancelAssignUser/{roleId}/{userIds}")
    @PreAuthorize("hasAuthority('system.role.assignUser')")
    @Log(title = "角色管理", businessType = BusinessTypeEnum.BATCH_CANCEL_ASSIGN)
    public R batchCancelAssignUser(@PathVariable @ApiParam("角色编号") Long roleId,
                                   @PathVariable @ApiParam("用户编号集合") List<Long> userIds) {
        sysUserRoleService.batchCancelAssignUser(roleId,userIds);
        return R.ok();
    }

    @ApiOperation(value = "获取所有角色信息")
    @ApiOperationSupport(order = 99)
    @GetMapping("/getRoleList")
    public R getRoleList() {
        List<SysRole> list = sysRoleService.list();
        return R.ok().data("list",list);
    }
}

