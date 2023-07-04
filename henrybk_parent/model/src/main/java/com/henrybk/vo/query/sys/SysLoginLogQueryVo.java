package com.henrybk.vo.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 登录日志查询实体
 * @author Henry
 * @since 2023-05-22
 */
@Data
@ApiModel(description = "登录日志查询实体")
public class SysLoginLogQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String loginId;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @ApiModelProperty(value = "登录状态（0失败 1成功）")
    private Integer status;

    @ApiModelProperty(value = "创建时间开始时间")
    private String createTimeBegin;

    @ApiModelProperty(value = "创建时间结束时间")
    private String createTimeEnd;
}
