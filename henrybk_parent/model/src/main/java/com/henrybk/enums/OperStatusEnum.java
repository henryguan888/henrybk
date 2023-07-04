package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 操作状态枚举类
 * @author Henry
 * @since 2023-05-26
 */
@Getter
@AllArgsConstructor
public enum OperStatusEnum {
    ABNORMAL(0, "异常"),
    NORMAL(1, "正常"),
    ;

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
