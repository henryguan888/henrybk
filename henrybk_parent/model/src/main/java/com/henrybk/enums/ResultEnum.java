package com.henrybk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 返回结果枚举类
 * @author Henry
 * @since 2023-05-16
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    /**
     * 成功的状态码 20000 开头
     */
    SUCCESS(20000, "操作成功"),
    LOGIN_SUCCESS(20000, "登录成功"),

    /**
     * 失败的状态码 20001 开始
     */
    ERROR(20001, "操作失败"),
    ACCOUNT_ALREADY_ERROR(20002, "账号已存在"),
    DICT_TYPE_EXIST(20009, "字典类型已存在"),


    ACCOUNT_EXPIRED(21001, "账户已过期"),
    ACCOUNT_PASSWORD_ERROR(21002, "用户名密码错误"),
    ACCOUNT_PASSWORD_EXPIRED(21003, "密码过期"),
    ACCOUNT_DISABLED(21004, "账户被禁用"),
    ACCOUNT_LOCK(21005, "账户已锁定，请联系管理员"),
    ACCOUNT_NUll(21006, "账户不存在"),


    UPD_PWD_TWO_ERROR(22001, "二次输入的密码不一致"),
    UPD_PWD_REPETITION_ERROR(22002, "新密码和旧密码不能相同"),
    UPD_PWD_ERROR(22003, "旧密码错误"),


    NO_PERMISSION(26001, "未授权访问"),


    PRIMARY_KEY_CONFLICT(29001, "主键冲突"),
    GET_CONFIG_ERROR(29801,"获取系统参数异常"),
    SYSTEM_ERROR(29999, "系统异常"),

    ;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private String msg;

}
