package com.henrybk.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysUser;
import com.henrybk.result.R;
import com.henrybk.system.service.SysUserService;
import com.henrybk.system.service.UserLoginService;
import com.henrybk.vo.query.sys.UpdatePwdQueryVo;
import com.henrybk.vo.vo.sys.LoginVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @description 后台用户登录 前端控制器
 * @author Henry
 * @since 2023-05-16
 */
@Api(tags = "登录管理-用户登录")
@ApiSupport(order = 0001)
@RestController
@RequestMapping("/admin/system/userLogin")
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "用户登录")
    @ApiOperationSupport(order = 1)
    @PostMapping("/login")
    public R login(@RequestBody @Valid @ApiParam("用户信息") LoginVo loginVo) {
        String token = userLoginService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "根据token获取用户信息")
    @ApiOperationSupport(order = 2)
    @GetMapping("/getInfo")
    public R getInfo(HttpServletRequest request) {
        //获取请求头token字符串
        String token = request.getHeader("token");
        Map<String,Object> map = sysUserService.getInfo(token);
        return R.ok().data(map);
    }

    @ApiOperation(value = "退出登录")
    @ApiOperationSupport(order = 3)
    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }

    @ApiOperation(value = "获取用户信息")
    @ApiOperationSupport(order = 4)
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        //获取请求头token字符串
        String token = request.getHeader("token");
        SysUserVo sysUserVo = sysUserService.getUserInfo(token);
        return R.ok().data("data", sysUserVo);
    }

    @ApiOperation(value = "修改基本信息")
    @ApiOperationSupport(order = 5)
    @PutMapping("/updateUserInfo")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateUserInfo(@RequestBody @ApiParam("用户信息实体") SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return R.ok();
    }

    @ApiOperation(value = "修改密码")
    @ApiOperationSupport(order = 6)
    @PutMapping("/updatePwd")
    public R updatePwd(@RequestBody @Valid @ApiParam("修改密码请求实体") UpdatePwdQueryVo updatePwdQueryVo,
                       HttpServletRequest request) {
        sysUserService.updatePwd(updatePwdQueryVo,request);
        return R.ok();
    }

    @ApiOperation(value = "获取系统用户、访问量、操作量、定时任务数据")
    @ApiOperationSupport(order = 7)
    @GetMapping("/getSystemData")
    public R getSystemData() {
        Map<String,Object> map = sysUserService.getSystemData();
        return R.ok().data(map);
    }

    @ApiOperation(value = "获取角色对应用户数量")
    @ApiOperationSupport(order = 8)
    @GetMapping("/getUserByRole")
    public R getUserByRole() {
        List<Map<String,Object>> list = sysUserService.getUserByRole();
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "获取近一周的登录和操作数据")
    @ApiOperationSupport(order = 9)
    @GetMapping("/getLoginAndOperData/{searchDate}")
    public R getLoginAndOperData(@ApiParam("查询日期") @PathVariable String searchDate) {
        Map<String,Object> map = sysUserService.getLoginAndOperData(searchDate);
        return R.ok().data(map);
    }
}