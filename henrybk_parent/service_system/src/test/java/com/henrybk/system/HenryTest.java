package com.henrybk.system;

import com.henrybk.enums.StatusEnum;
import com.henrybk.utils.EnumUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description
 * @author Henry
 * @since 2023-06-19
 */
@SpringBootTest
public class HenryTest {


    @Test
    public void test01() {
        Integer status = 1;
        StatusEnum statusEnum = EnumUtil.getEnumByCode(StatusEnum.class,status);
        System.out.println(statusEnum.getName());
    }
}
