package com.henrybk.vo.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 前端路由菜单对象
 * @author Henry
 * @since 2023-05-19
 */
@Data
@ApiModel(description = "前端路由菜单对象")
public class RouterVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "路由对应的组件")
    private String component;

    @ApiModelProperty(value = "是否隐藏菜单（0显示 1隐藏）")
    private boolean hidden;

    @ApiModelProperty(value = "是否显示根路由")
    private boolean alwaysShow;


    @ApiModelProperty(value = "路由meta信息")
    private Meta meta;

    @ApiModelProperty(value = "子路由")
    private List<RouterVo> children;

    @Data
    @AllArgsConstructor
    public class Meta {
        @ApiModelProperty(value = "标题")
        private String title;
        @ApiModelProperty(value = "图标")
        private String icon;
    }

}
