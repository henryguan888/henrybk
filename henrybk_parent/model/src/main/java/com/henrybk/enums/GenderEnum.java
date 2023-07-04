package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 性别枚举类
 * @author Henry
 * @since 2023-06-14
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(2, "保密"),
    ;
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
