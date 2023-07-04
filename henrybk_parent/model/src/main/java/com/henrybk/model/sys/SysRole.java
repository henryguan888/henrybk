package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description 角色信息
 * @author Henry
 * @since 2023-05-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "角色信息实体")
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty(value = "显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限）")
    @TableField("data_scope")
    private Integer dataScope;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}
