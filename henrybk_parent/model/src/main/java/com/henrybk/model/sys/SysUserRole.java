package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description 用户角色关联实体
 * @author Henry
 * @since 2023-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "用户角色关联实体")
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户编号")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "角色编号")
    @TableField("role_id")
    private Long roleId;

}
