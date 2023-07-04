package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 菜单类型枚举类
 * @author Henry
 * @since 2023-05-30
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    DIRECTORY(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮"),
    ;
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
