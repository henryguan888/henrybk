package com.henrybk.vo.query.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 系统配置查询对象
 * @author Henry
 * @since 2023-05-22
 */
@Data
@ApiModel(description = "系统配置查询对象")
public class SysConfigQueryVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "参数键名")
    @TableField("config_key")
    private String configKey;

    @ApiModelProperty(value = "参数名称")
    @TableField("config_name")
    private String configName;

    @ApiModelProperty(value = "创建时间起始时间")
    private String createTimeBegin;

    @ApiModelProperty(value = "创建时间结束时间")
    private String createTimeEnd;
}
