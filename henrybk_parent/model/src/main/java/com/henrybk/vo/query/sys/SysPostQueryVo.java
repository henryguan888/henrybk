package com.henrybk.vo.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 岗位查询对象
 * @author Henry
 * @since 2023-05-20
 */
@Data
@ApiModel(description = "岗位查询对象")
public class SysPostQueryVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    private String name;

    @ApiModelProperty(value = "状态（0停用 1正常）")
    private Integer status;

    @ApiModelProperty(value = "创建时间起始时间")
    private String createTimeBegin;

    @ApiModelProperty(value = "创建时间结束时间")
    private String createTimeEnd;
}
