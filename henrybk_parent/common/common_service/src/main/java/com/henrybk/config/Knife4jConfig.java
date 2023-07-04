package com.henrybk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @description knife4j配置信息（swagger2增强）
 * @author Henry
 * @since 2023-05-16
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket adminApiConfig(){
        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.henrybk"))
                //只显示admin路径下的页面
                .paths(PathSelectors.regex("/admin/.*")) // PathSelectors.any()
                .build()
                .globalOperationParameters(this.configPars());
    }
    @Bean
    public Docket userApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("userApi")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.henrybk"))
                .paths(PathSelectors.regex("/user/.*"))
                .build()
                .globalOperationParameters(this.configPars());
    }

    @Bean
    public Docket ThirdApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("thirdApi")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.henrybk"))
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .globalOperationParameters(this.configPars());
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("HENRYBK后台管理系统-API文档")
                .description("本文档描述了HENRYBK后台管理系统接口定义")
                .version("1.0")
                .contact(new Contact("HENRYBK后台管理系统", "http://localhost:9999/", "15879208791@163.com"))
                .build();
    }

    private List<Parameter> configPars() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token")
                .description("用户token")
                .defaultValue("")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        pars.add(tokenPar.build());
        return pars;
    }

}
