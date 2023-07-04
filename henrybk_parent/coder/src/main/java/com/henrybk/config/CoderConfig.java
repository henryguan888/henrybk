package com.henrybk.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Henry Guan
 * @description
 * @since 2023-05-12
 */

public class CoderConfig {

    public Coder getCoderConfig() throws IOException {
        Resource resource = new ClassPathResource("application.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        Coder coder = new Coder();
        coder.setParentDir(properties.getProperty("coder.parentDir"));
        coder.setOutputDir(properties.getProperty("coder.outputDir"));
        coder.setAuthor(properties.getProperty("coder.author"));
        coder.setDataUrl(properties.getProperty("coder.dataUrl"));
        coder.setDriverName(properties.getProperty("coder.driverName"));
        coder.setUsername(properties.getProperty("coder.username"));
        coder.setPassword(properties.getProperty("coder.password"));
        coder.setTableName(properties.getProperty("coder.tableName"));
        coder.setModuleName(properties.getProperty("coder.moduleName"));
        coder.setParent(properties.getProperty("coder.parent"));
        return coder;
    }
}
