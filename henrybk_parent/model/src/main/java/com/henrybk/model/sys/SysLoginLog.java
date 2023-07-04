package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.enums.LoginStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 登录日志表
 * @author Henry
 * @since 2023-05-18
 */
@Data
@ApiModel(description = "登录日志实体")
@TableName("sys_login_log")
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录账号")
    @TableField("login_id")
    private String loginId;

    @ApiModelProperty(value = "会话编号")
    @TableField("session_id")
    private String sessionId;

    @ApiModelProperty(value = "登录IP")
    @TableField("login_ip")
    private String loginIp;

    @ApiModelProperty(value = "登录地址")
    @TableField("login_address")
    private String loginAddress;

    @ApiModelProperty(value = "登录浏览器")
    @TableField("browser")
    private String browser;

    @ApiModelProperty(value = "登录操作系统")
    @TableField("operating_system")
    private String operatingSystem;

    @ApiModelProperty(value = "登录时间")
    @TableField("login_time")
    private Date loginTime;

    @ApiModelProperty(value = "登录状态（0失败 1成功）")
    @TableField("status")
    private LoginStatusEnum status;

    @ApiModelProperty(value = "操作信息")
    @TableField("message")
    private String message;

}
