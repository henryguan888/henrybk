package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 登录状态枚举
 * @author Henry
 * @since 2023-05-22
 */
@Getter
@AllArgsConstructor
public enum LoginStatusEnum {
    FAIL(0, "失败"),
    SUCCESS(1, "成功"),
    ;
    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
