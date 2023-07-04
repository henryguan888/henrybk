package com.henrybk.vo.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 用户查询实体
 * @author Henry
 * @since 2023-05-20
 */
@Data
@ApiModel(description = "用户查询实体")
public class SysUserQueryVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "关键词")
    private String keyword;

    @ApiModelProperty(value = "部门编号")
    private Long deptId;

    @ApiModelProperty(value = "部门编号集合")
    private List<Long> deptIds;

    @ApiModelProperty(value = "岗位编号")
    private Long postId;

    @ApiModelProperty(value = "帐号状态（0:停用，1:正常）")
    private Integer status;

    @ApiModelProperty(value = "创建时间开始时间")
    private String createTimeBegin;

    @ApiModelProperty(value = "创建时间结束时间")
    private String createTimeEnd;
}
