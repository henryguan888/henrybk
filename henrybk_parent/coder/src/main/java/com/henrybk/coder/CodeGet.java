package com.henrybk.coder;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.henrybk.config.Coder;
import com.henrybk.config.CoderConfig;

import java.io.IOException;

/**
 * @描述 代码生成器
 * @作者 Henry Guan
 * @日期 2023年03月09日 17:29
 */
public class CodeGet {

    public static void main(String[] args) throws IOException {

        CoderConfig coderConfig = new CoderConfig();
        Coder coder = coderConfig.getCoderConfig();

        // 1.创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2.全局配置
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // 格式：projectPath + "/src/main/java"
        gc.setOutputDir(coder.getOutputDir() + "/src/main/java");
        // %s是为了去掉service接口的首字母I（e.g:IUserService）
        gc.setServiceName("%sService");
        gc.setAuthor(coder.getAuthor());
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 3.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(coder.getDataUrl());
        dsc.setDriverName(coder.getDriverName());
        dsc.setUsername(coder.getUsername());
        dsc.setPassword(coder.getPassword());
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4.包配置
        PackageConfig pc = new PackageConfig();
        // 模块名
        pc.setModuleName(coder.getModuleName());
        pc.setParent(coder.getParent());

        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5.策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名
        strategy.setInclude(coder.getTableName());
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);

        // 数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);

        // restful api风格控制器
        strategy.setRestControllerStyle(true);
        // url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);

        // 6.执行
        mpg.execute();
    }
}
