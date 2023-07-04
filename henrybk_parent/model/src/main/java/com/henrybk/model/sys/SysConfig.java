package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description 系统配置实体
 * @author Henry
 * @since 2023-05-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "系统配置实体")
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "参数键名")
    @TableField("config_key")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    @TableField("config_value")
    private String configValue;

    @ApiModelProperty(value = "参数名称")
    @TableField("config_name")
    private String configName;



}
