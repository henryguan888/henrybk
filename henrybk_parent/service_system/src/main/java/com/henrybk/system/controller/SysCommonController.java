package com.henrybk.system.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.henrybk.model.base.EnumBaseEntity;
import com.henrybk.result.R;
import com.henrybk.utils.EnumUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 字典数据 前端控制器
 * @author Henry
 * @since 2023-05-24
 */
@Api(tags = "系统管理-通用接口")
@ApiSupport(order = 9899)
@RestController
@RequestMapping("/admin/system/sysCommon")
public class SysCommonController {

    @ApiOperation(value = "根据枚举类名获取枚举集合")
    @GetMapping("/getEnumList/{clazzName}")
    public R getEnumList(@PathVariable String clazzName) {
        List<EnumBaseEntity> list = EnumUtil.enumToList(clazzName);
        return R.ok().data("list",list);
    }


}

