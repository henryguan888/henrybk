package com.henrybk.vo.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 菜单查询对象
 * @author Henry
 * @since 2023-05-30
 */
@Data
@ApiModel(description = "菜单查询对象")
public class SysMenuQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "状态（0停用 1正常）")
	private Integer status;

	@ApiModelProperty(value = "创建时间起始时间")
	private String createTimeBegin;

	@ApiModelProperty(value = "创建时间结束时间")
	private String createTimeEnd;
}

