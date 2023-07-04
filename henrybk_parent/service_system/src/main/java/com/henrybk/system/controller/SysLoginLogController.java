package com.henrybk.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.model.sys.SysLoginLog;
import com.henrybk.result.R;
import com.henrybk.system.service.SysLoginLogService;
import com.henrybk.vo.query.sys.SysLoginLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description 登录日志 前端控制器
 * @author Henry
 * @since 2023-05-20
 */
@Api(tags = "系统监控-登录日志")
@ApiSupport(order = 9806)
@RestController
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "分页查询登录日志列表")
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("登录日志查询实体") SysLoginLogQueryVo sysLoginLogQueryVo) {
        Page<SysLoginLog> page = sysLoginLogService.getSysLoginLogPage(pageNum,pageSize,sysLoginLogQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

}

