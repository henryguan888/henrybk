package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.enums.FlagEnum;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @description 字典数据表
 * @author Henry
 * @since 2023-05-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "字典数据实体")
@TableName("sys_dict_data")
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "父编号")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "字典标签")
    @TableField("dict_label")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    @TableField("dict_value")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty(value = "显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "是否默认（0否 1是）")
    @TableField("is_default")
    private FlagEnum isDefault;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "下级字典")
    @TableField(exist = false)
    private List<SysDictData> children;

    @ApiModelProperty(value = "是否包含子节点")
    @TableField(exist = false)
    private boolean hasChildren;

}
