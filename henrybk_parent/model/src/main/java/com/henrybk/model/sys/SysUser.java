package com.henrybk.model.sys;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.enums.GenderEnum;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @description 用户信息
 * @author Henry
 * @since 2023-05-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "用户信息实体")
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "部门编号")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "岗位编号")
    @TableField("post_id")
    private Long postId;

    @ApiModelProperty(value = "用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "用户性别（0女 1男 2未知）")
    @TableField("gender")
    private GenderEnum gender;

    @ApiModelProperty(value = "头像地址")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "帐号状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "密码输入错误次数(初期为0，登录成功后变为0，密码输入错误后累计，累计5次，该用户将被锁定，密码重置或用户解锁后，变为0)")
    @TableField("pwd_err_num")
    private Integer pwdErrNum;

    @ApiModelProperty(value = "最后登录IP")
    @TableField("login_ip")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("login_date")
    private Date loginDate;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}
