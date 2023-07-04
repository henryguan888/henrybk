package com.henrybk.vo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 在线用户对象
 * @author Henry
 * @since 2023-05-22
 */
@Data
@ApiModel(description = "在线用户对象")
public class SysOnlineVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "会话编号")
    private String sessionId;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "登录账号")
    private String loginId;

    @ApiModelProperty(value = "用户部门")
    private String userDept;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @ApiModelProperty(value = "登录地址")
    private String loginAddress;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;


}
