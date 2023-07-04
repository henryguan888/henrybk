package com.henrybk.utils;


import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.henrybk.constants.TypeCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @description IP地址工具类
 * @author Henry
 * @since 2023-05-22
 */
@Slf4j
public class AddressUtil {

    // 未知地址
    public static final String UNKNOWN = "未知地址";


    public static String getRealAddressByIP(String ip) {
        try {
            // 内网不查询
            if (IpUtil.internalIp(ip)) {
                return "内网IP";
            }
            HTTP http = HTTP.builder().build();
            String rspStr = http.sync(TypeCode.IP_URL).addPathPara("ip", ip).get().getBody().toString();
            if (!StringUtils.hasLength(rspStr)) {
                log.error("网络连接错误,获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("网络连接错误,获取地理位置异常 {}", e);
            return UNKNOWN;
        }
    }

    public static void main(String[] args) {
        String address = getRealAddressByIP("121.15.205.146");
        System.out.println(address);
    }

}
