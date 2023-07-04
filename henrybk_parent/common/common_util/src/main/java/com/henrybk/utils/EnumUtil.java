package com.henrybk.utils;

import com.henrybk.constants.TypeCode;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.base.EnumBaseEntity;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 通用枚举工具类
 * @author Henry
 * @since 2023-06-14
 */
@Log4j2
public class EnumUtil {

   /*
    * 通过类名获取枚举类数据List列表
    * @param clazzName
    * @return List<EnumBaseEntity>
    */
    public static <T extends Enum<T>> List<EnumBaseEntity> enumToList(String clazzName) {
        List<EnumBaseEntity> list = new ArrayList<>();

        try {
            Class<?> enumClass = Class.forName(TypeCode.ENUMS_PATH + clazzName);
            if (enumClass.isEnum()) {
                list = enumToList((Class<Enum>) enumClass);
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }


    /*
     * 通过类名获取枚举类数据Map集合
     * @param clazzName
     * @return Map<Integer,Object>
     */
    public static <T extends Enum<T>> Map<Integer, Object> enumToMap(String clazzName) {
        Map<Integer, Object> map = new HashMap<>();
        try {
            Class<?> enumClass = Class.forName(TypeCode.ENUMS_PATH + clazzName);
            if (enumClass.isEnum()) {
                map = enumToMap((Class<Enum>) enumClass);
            }
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    public static <T extends Enum<T>> T getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        Class<T> enumClass = (Class<T>) StatusEnum.class;
        Map<Integer, T> codeEnumMap = getCodeEnumMap(enumClass);
        return codeEnumMap.get(code);
    }

    public static <T extends Enum<T>> T getEnumByCode(Class<T> enumClass, Integer code) {
        if (code == null) {
            return null;
        }
        Map<Integer, T> codeEnumMap = getCodeEnumMap(enumClass);
        return codeEnumMap.get(code);
    }

    public static <T extends Enum<T>> Map<Integer, T> getCodeEnumMap(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(Enum::ordinal, e -> e, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static List<EnumBaseEntity> enumToList(Class<Enum> enumClass) throws Exception {
        List<EnumBaseEntity> list = new ArrayList<>();
        Enum[] enums = enumClass.getEnumConstants();

        Field codeField = enumClass.getDeclaredField("code");
        Field valueField = enumClass.getDeclaredField("name");
        codeField.setAccessible(true);
        valueField.setAccessible(true);

        for (Enum en : enums) {
            Integer code = (Integer) codeField.get(en);
            String value = (String) valueField.get(en);
            EnumBaseEntity entity = new EnumBaseEntity(code, value);
            list.add(entity);
        }

        return list;
    }


    private static Map<Integer, Object> enumToMap(Class<Enum> enumClass) throws Exception {
        Map<Integer, Object> map = new HashMap<>();
        Enum[] enums = enumClass.getEnumConstants();

        Field codeField = enumClass.getDeclaredField("code");
        Field valueField = enumClass.getDeclaredField("name");
        codeField.setAccessible(true);
        valueField.setAccessible(true);

        for (Enum en : enums) {
            Integer code = (Integer) codeField.get(en);
            Object value = valueField.get(en);
            map.put(code, value);
        }

        return map;
    }
}
