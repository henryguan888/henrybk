package com.henrybk.vo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 日期总数对象
 * @author Henry
 * @since 2023-06-26
 */
@ApiModel(description = "日期总数对象")
@Data
public class DateCountVo {
    @ApiModelProperty(value = "日期")
    private String countDate;

    @ApiModelProperty(value = "总数")
    private Integer countNum;
}
