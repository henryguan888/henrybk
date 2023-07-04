package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 默认头像枚举类
 * @author Henry
 * @since 2023-06-17
 */
@Getter
@AllArgsConstructor
public enum AvatarEnum {
    FEMALE(0, "/static/girl.gif"),
    MALE(1, "/static/boy.gif"),
    UNKNOWN(2, "/static/unknown.gif"),
    ;
    @EnumValue
    private Integer code;
    private String name;

    public static String getNameByCode(Integer code) {
        AvatarEnum[] arrObj = AvatarEnum.values();
        for (AvatarEnum obj : arrObj) {
            if (obj.getCode().intValue() == code.intValue()) {
                return obj.getName();
            }
        }
        return "";
    }

}
