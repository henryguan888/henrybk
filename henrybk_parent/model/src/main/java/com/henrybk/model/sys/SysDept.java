package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @description 组织架构表
 * @author Henry
 * @since 2023-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "组织架构实体")
@TableName("sys_dept")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "部门名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "上级部门id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "树结构")
    @TableField("tree_path")
    private String treePath;

    @ApiModelProperty(value = "显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "负责人")
    @TableField("leader")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱地址")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "子部门")
    @TableField(exist = false)
    private List<SysDept> children;

}
