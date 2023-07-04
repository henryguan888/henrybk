package com.henrybk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description 配置文件读取类
 * @author Henry
 * @since 2023-06-03
 */
@Component
@Data
@PropertySource(value = {"classpath:custom.properties"},encoding = "utf-8")
public class PropertiesConfig {
    /*
    方式一：@Value(“${oss.file.endpoint}”) + 类上@Component
    方式二：@ConfigurationProperties(prefix = "oss.file") + 主启动类加@EnableConfigurationProperties(value = OssProperties.class)
    方式三：@ConfigurationProperties(prefix = "oss.file") + @Component + @PropertySource(value = {"classpath:oss.properties"})
     */

    @Value("${henry.password.salt}")
    private String passwordSalt;
}