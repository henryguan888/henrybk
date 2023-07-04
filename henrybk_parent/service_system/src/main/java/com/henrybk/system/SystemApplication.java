package com.henrybk.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description 主启动类
 * @author Henry
 * @since 2023-05-15
 */
@SpringBootApplication
@ComponentScan(value = "com.henrybk")//扫描当前模块和引入模块下包
@MapperScan(value = "com.henrybk.system.mapper")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}
