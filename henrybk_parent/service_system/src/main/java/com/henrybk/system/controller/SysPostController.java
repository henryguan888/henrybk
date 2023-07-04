package com.henrybk.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.annotation.Log;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.model.sys.SysPost;
import com.henrybk.result.R;
import com.henrybk.system.service.SysPostService;
import com.henrybk.vo.query.sys.SysPostQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description 岗位信息 前端控制器
 * @author Henry
 * @since 2023-05-20
 */
@Api(tags = "系统管理-岗位管理")
@ApiSupport(order = 9805)
@RestController
@RequestMapping("/admin/system/sysPost")
public class SysPostController {

    @Resource
    private SysPostService sysPostService;

    @ApiOperation(value = "分页查询获取岗位信息列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getPageList/{pageNum}/{pageSize}")
    @PreAuthorize("hasAuthority('system.post.query')")
    public R getPageList(@PathVariable @ApiParam("当前页") Integer pageNum,
                         @PathVariable @ApiParam("每页记录数") Integer pageSize,
                         @ApiParam("岗位查询对象") SysPostQueryVo sysPostQueryVo) {
        Page<SysPost> page = sysPostService.getSysPostPage(pageNum,pageSize,sysPostQueryVo);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

    @ApiOperation(value = "添加岗位信息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system.post.add')")
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.INSERT)
    public R save(@RequestBody @ApiParam("用户信息实体") SysPost sysPost) {
        sysPostService.save(sysPost);
        return R.ok();
    }

    @ApiOperation("根据id查询岗位信息")
    @ApiOperationSupport(order = 3)
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('system.post.edit')")
    public R getById(@PathVariable @ApiParam("岗位编号") Long id) {
        SysPost sysPost = sysPostService.getById(id);
        return R.ok().data("data",sysPost);
    }

    @ApiOperation("根据id修改用户信息")
    @ApiOperationSupport(order = 4)
    @PutMapping("/updateById")
    @PreAuthorize("hasAuthority('system.post.edit')")
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.UPDATE)
    public R updateById(@RequestBody @ApiParam("用户信息实体") SysPost sysPost) {
        sysPostService.updateById(sysPost);
        return R.ok();
    }

    @ApiOperation(value = "根据id更新岗位状态")
    @ApiOperationSupport(order = 5)
    @GetMapping("/updateStatus/{id}/{status}")
    @PreAuthorize("hasAuthority('system.post.edit')")
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.UPDATE_STATUS)
    public R updateStatus(@PathVariable @ApiParam("岗位编号") Long id,
                          @PathVariable @ApiParam("岗位状态（0停用 1正常）") Integer status) {
        sysPostService.updateStatus(id, status);
        return R.ok();
    }

    @ApiOperation("根据id逻辑删除岗位信息")
    @ApiOperationSupport(order = 6)
    @DeleteMapping("/removeById/{id}")
    @PreAuthorize("hasAuthority('system.post.del')")
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.DELETE)
    public R removeById(@PathVariable @ApiParam("岗位编号") Long id) {
        sysPostService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除岗位信息")
    @ApiOperationSupport(order = 7)
    @DeleteMapping("/batchRemove/{ids}")
    @PreAuthorize("hasAuthority('system.post.del')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.BATCH_DEL)
    public R batchRemove(@PathVariable @ApiParam("id集合") List<Long> ids) {
        sysPostService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation(value="导出")
    @ApiOperationSupport(order = 8)
    @GetMapping(value = "/download")
    @PreAuthorize("hasAuthority('system.post.download')")
    @Log(title = "用户管理", businessType = BusinessTypeEnum.DOWNLOAD)
    public void download(HttpServletResponse response) {
        sysPostService.download(response);
    }


    @ApiOperation(value = "获取所有岗位信息")
    @ApiOperationSupport(order = 9)
    @GetMapping("/getPostList")
    public R getPostList() {
        List<SysPost> list = sysPostService.list();
        return R.ok().data("list",list);
    }
}

