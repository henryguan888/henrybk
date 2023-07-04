package com.henrybk.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.henrybk.constants.TypeCode;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description Mybatis Plus自定义实现类
 * @author Henry
 * @since 2023-05-19
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("isDeleted", TypeCode.FLAG_ZERO, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
