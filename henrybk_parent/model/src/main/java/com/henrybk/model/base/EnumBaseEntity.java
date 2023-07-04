package com.henrybk.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 枚举实体
 * @author Henry
 * @since 2023-06-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnumBaseEntity implements Serializable {
    private Integer code;
    private String name;

}
