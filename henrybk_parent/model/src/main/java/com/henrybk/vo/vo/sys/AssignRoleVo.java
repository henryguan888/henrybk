package com.henrybk.vo.vo.sys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description 分配角色对象
 * @author Henry
 * @since 2023-05-20
 */
@ApiModel(description = "分配角色")
@Data
public class AssignRoleVo {

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "角色编号列表")
    private List<Long> roleIdList;

}
