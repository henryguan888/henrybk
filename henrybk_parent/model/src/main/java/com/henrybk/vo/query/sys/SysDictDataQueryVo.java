package com.henrybk.vo.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 字典数据查询对象
 * @author Henry
 * @since 2023-05-30
 */
@Data
@ApiModel(description = "字典数据查询对象")
public class SysDictDataQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "字典编号")
	private Long id;

	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@ApiModelProperty(value = "字典标签")
	private String dictLabel;

	@ApiModelProperty(value = "状态（0停用 1正常）")
	private Integer status;

	@ApiModelProperty(value = "创建时间起始时间")
	private String createTimeBegin;

	@ApiModelProperty(value = "创建时间结束时间")
	private String createTimeEnd;
}

