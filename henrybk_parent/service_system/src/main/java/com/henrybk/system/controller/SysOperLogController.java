package com.henrybk.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.model.sys.SysOperLog;
import com.henrybk.result.R;
import com.henrybk.system.service.SysOperLogService;
import com.henrybk.vo.query.sys.SysOperLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description 操作日志记录 前端控制器
 * @author Henry
 * @since 2023-05-26
 */
@Api(tags = "系统监控-操作日志")
@ApiSupport(order = 9807)
@RestController
@RequestMapping("/admin/system/sysOperLog")
public class SysOperLogController {

    @Resource
    private SysOperLogService sysOperLogService;

    @ApiOperation(value = "分页查询操作日志列表")
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("操作日志查询实体") SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> page = sysOperLogService.getPageList(pageNum,pageSize,sysOperLogQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }
}

