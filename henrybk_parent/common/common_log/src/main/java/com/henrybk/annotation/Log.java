package com.henrybk.annotation;


import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.enums.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * @description 自定义日志注解
 * @author Henry
 * @since 2023-05-22
 */
@Target({ElementType.PARAMETER,ElementType.METHOD}) //用在描述参数、方法上
@Retention(RetentionPolicy.RUNTIME)//作用范围
@Documented
public @interface Log {
    /**
     * 模块标题
     */
    public String title() default "";

    /**
     * 业务类型（0其它 1新增 2修改 3删除...）
     */
    public BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    public OperatorTypeEnum operatorType() default OperatorTypeEnum.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
