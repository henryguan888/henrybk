package com.henrybk.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description 监听启动类打印banner
 * @author Henry
 * @since 2023-06-08
 */
@Component
public class PrintBanner implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println(
                " ██    ██    ████████    ███   ██    ██████▒    ███    ███   ██████▒     ██   ███ \n" +
                " ██    ██    ████████    ███   ██    ███████▓   ░██▒  ▒██░   ███████▓    ██  ▓██  \n" +
                " ██    ██    ██          ███▒  ██    ██   ▒██    ███  ███    ██   ▒██    ██ ▒██▒  \n" +
                " ██    ██    ██          ████  ██    ██    ██     ██▒▒██     ██    ██    ██░██▒   \n" +
                " ██    ██    ██          ██▒█▒ ██    ██   ▒██     ▓████▓     ██   ▒██    █████    \n" +
                " ████████    ███████     ██ ██ ██    ███████▒      ████      ███████░    █████    \n" +
                " ████████    ███████     ██ ██ ██    ██████▓       ▒██▒      ███████░    █████▒   \n" +
                " ██    ██    ██          ██ ▒█▒██    ██  ▓██░       ██       ██   ▒██    ██▒▒██   \n" +
                " ██    ██    ██          ██  ████    ██   ██▓       ██       ██    ██    ██  ██▓  \n" +
                " ██    ██    ██          ██  ▒███    ██   ▒██       ██       ██   ▒██    ██  ▒██  \n" +
                " ██    ██    ████████    ██   ███    ██    ██▒      ██       ████████    ██   ██▓ \n" +
                " ██    ██    ████████    ██   ███    ██    ███      ██       ██████▓     ██   ▒██ \n" +
                "版权所有，严禁搬运");
        System.out.println("后端服务地址：http://localhost:8888/");
        System.out.println("项目访问地址：http://localhost:9999/");
        System.out.println("项目API文档地址：http://localhost:8888/doc.html");
    }
}
