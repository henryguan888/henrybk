package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 操作类别枚举类
 * @author Henry
 * @since 2023-05-26
 */
@Getter
@AllArgsConstructor
public enum OperatorTypeEnum {
    OTHER(0, "其他"),
    WEB(1, "后台用户"),
    PHONE(2, "手机端用户"),

    ;
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
