package com.henrybk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrybk.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @description 封装认证授权响应信息工具类
 * @author Henry
 * @since 2023-06-15
 */
public class ResponseUtil {
    

    public static void out(HttpServletResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}