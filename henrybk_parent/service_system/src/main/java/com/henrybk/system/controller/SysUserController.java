package com.henrybk.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysUser;
import com.henrybk.result.R;
import com.henrybk.system.service.SysUserRoleService;
import com.henrybk.system.service.SysUserService;
import com.henrybk.vo.vo.sys.AssignRoleVo;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description 用户信息 前端控制器
 * @author Henry
 * @since 2023-05-15
 */
@Api(tags = "系统管理-用户管理")
@ApiSupport(order = 9801)
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "分页查询获取用户信息列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.user.query')")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("用户信息对象") SysUserQueryVo sysUserQueryVo) {
        IPage<SysUserVo> page = sysUserService.getUserInfoPage(pageNum,pageSize,sysUserQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "添加用户信息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.user.add')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("用户信息实体") SysUser sysUser) {
        sysUserService.saveUser(sysUser);
        return R.ok();
    }

    @ApiOperation("根据id查询用户信息")
    @ApiOperationSupport(order = 3)
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('system.user.edit')")
    public R getById(@PathVariable @ApiParam("用户编号") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser,sysUserVo);
        return R.ok().data("data",sysUserVo);
    }

    @ApiOperation("根据id修改用户信息")
    @ApiOperationSupport(order = 4)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.user.edit')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("用户信息实体") SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return R.ok();
    }

    @ApiOperation(value = "根据id更新用户状态")
    @ApiOperationSupport(order = 5)
    @GetMapping("/updateStatus/{id}/{status}")
    @PreAuthorize("hasAuthority('system.user.edit')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.UPDATE_STATUS)
    public R updateStatus(@PathVariable @ApiParam("用户编号") Long id,
                          @PathVariable @ApiParam("帐号状态（0停用 1正常）") Integer status) {
        sysUserService.updateStatus(id, status);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除用户信息")
    @ApiOperationSupport(order = 6)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.user.del')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("用户编号") Long id) {
        sysUserService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除用户信息")
    @ApiOperationSupport(order = 7)
    @DeleteMapping("/batchRemove/{ids}")
    @PreAuthorize("hasAuthority('system.user.del')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.BATCH_DEL)
    public R batchRemoveSysUser(@PathVariable @ApiParam("id集合") List<Long> ids) {
        sysUserService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation(value="导入")
    @ApiOperationSupport(order = 8)
    @PostMapping(value = "/upload")
    @PreAuthorize("hasAuthority('system.user.upoad')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.UPLOAD)
    public R upload(MultipartFile file) throws IOException {
        sysUserService.upload(file);
        return R.ok();
    }

    @ApiOperation(value="导出")
    @ApiOperationSupport(order = 9)
    @GetMapping(value = "/download")
    @PreAuthorize("hasAuthority('system.user.download')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.DOWNLOAD)
    public void download(HttpServletResponse response) {
        sysUserService.download(response);
    }

    @ApiOperation(value = "重置密码")
    @ApiOperationSupport(order = 10)
    @GetMapping("/restPassword/{userId}")
    @PreAuthorize("hasAuthority('system.user.restPwd')")
    public R restPassword(@PathVariable @ApiParam("用户编号") Long userId) {
        sysUserService.restPassword(userId);
        return R.ok();
    }

    @ApiOperation(value = "获取用户的角色数据")
    @ApiOperationSupport(order = 11)
    @GetMapping("/toAssignRole/{userId}")
    @PreAuthorize("hasAuthority('system.user.assignRole')")
    public R toAssignRole(@PathVariable @ApiParam("用户编号") Long userId) {
        Map<String, Object> roleMap = sysUserRoleService.toAssign(userId);
        return R.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @ApiOperationSupport(order = 12)
    @PostMapping("/doAssignRole")
    @PreAuthorize("hasAuthority('system.user.assignRole')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.ASSIGN)
    public R doAssignRole(@RequestBody @ApiParam("分配用户角色") AssignRoleVo assignRoleVo) {
        sysUserRoleService.doAssign(assignRoleVo);
        return R.ok();
    }

}

