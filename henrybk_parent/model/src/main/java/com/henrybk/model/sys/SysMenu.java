package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.henrybk.enums.AlwaysShowEnum;
import com.henrybk.enums.HiddenEnum;
import com.henrybk.enums.MenuTypeEnum;
import com.henrybk.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 菜单权限表
 * @author Henry
 * @since 2023-05-18
 */
@Data
@ApiModel(description = "菜单权限实体")
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "父菜单编号")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "路由地址")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "组件路径")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "菜单类型菜单类型（0目录 1菜单 2按钮）")
    @TableField("menu_type")
    private MenuTypeEnum menuType;

    @ApiModelProperty(value = "是否显示根路由（0隐藏 1显示）")
    @TableField("always_show")
    private AlwaysShowEnum alwaysShow;

    @ApiModelProperty(value = "是否隐藏菜单（0显示 1隐藏）")
    @TableField("hidden")
    private HiddenEnum hidden;

    @ApiModelProperty(value = "菜单状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "权限标识")
    @TableField("perms")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除(0未删除 1已删除)")
    @TableLogic //表示该字段是逻辑删除字段
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    private Integer isDeleted;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<SysMenu> children;

    @ApiModelProperty(value = "是否选中")
    @TableField(exist = false)
    private boolean isSelect;
}
