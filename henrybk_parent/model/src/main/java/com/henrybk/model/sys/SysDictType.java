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
 * @description 字典类型表
 * @author Henry
 * @since 2023-05-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "字典类型实体")
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "字典名称")
    @TableField("dict_name")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}
