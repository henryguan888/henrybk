package com.henrybk.vo.query.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 操作日志查询
 * @author Henry
 * @since 2023-05-26
 */
@Data
@ApiModel(description = "操作日志查询实体")
public class SysOperLogQueryVo implements Serializable {

    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "业务类型")
    private Integer businessType;


    @ApiModelProperty(value = "操作人员")
    @TableField("oper_name")
    private String operName;

    @ApiModelProperty(value = "操作状态（0异常 1正常）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间开始时间")
    private String createTimeBegin;

    @ApiModelProperty(value = "创建时间结束时间")
    private String createTimeEnd;
}
