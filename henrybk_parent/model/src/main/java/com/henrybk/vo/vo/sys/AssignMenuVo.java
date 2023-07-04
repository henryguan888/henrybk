package com.henrybk.vo.vo.sys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
/**
 * @description 分配菜单
 * @author Henry
 * @since 2023-05-21
 */
@ApiModel(description = "分配菜单")
@Data
public class AssignMenuVo {

    @ApiModelProperty(value = "角色编号")
    private Long roleId;

    @ApiModelProperty(value = "菜单编号列表")
    private List<Long> menuIdList;

}
