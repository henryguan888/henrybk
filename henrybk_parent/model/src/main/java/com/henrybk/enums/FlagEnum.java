package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 通用标志枚举类
 * @author Henry
 * @since 2023-06-14
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {
    NO(0, "否"),
    YES(1, "是"),
    ;
    @EnumValue //标注哪一个字段是数据库里的字段
    @JsonValue //标注要开启自定义序列化返回值
    private Integer code;
    private String name;

}
