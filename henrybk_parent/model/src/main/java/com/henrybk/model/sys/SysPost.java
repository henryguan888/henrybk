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
 * @description 岗位信息表
 * @author Henry
 * @since 2023-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "岗位信息实体")
@TableName("sys_post")
public class SysPost extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "岗位编码")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    @TableField("status")
    private StatusEnum status;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}
