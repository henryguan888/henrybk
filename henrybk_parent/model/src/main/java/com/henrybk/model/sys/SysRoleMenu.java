package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description 角色菜单关联表
 * @author Henry
 * @since 2023-05-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="色菜单关联实体")
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "角色编号")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "菜单编号")
    @TableField("menu_id")
    private Long menuId;

}
