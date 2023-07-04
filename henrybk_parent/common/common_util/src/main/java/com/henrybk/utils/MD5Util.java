package com.henrybk.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @description MD5加密工具类
 * @author Henry
 * @since 2023-05-16
 */
public class MD5Util {

    private static final String salt="1a2b3c4d5e6f7g8h9i0j";

    /**
     * md5加密
     * @param src
     * @return
     */
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 固定盐 md5加密
     * @param inputPass
     * @return
     */
    public static String md5Salt(String inputPass){
        String st=""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(st);
    }

    /**
     * 自定义盐 加密
     * @param formPass
     * @param salt
     * @return
     */
    public static String md5Salt(String formPass,String salt){
        String st=""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(st);
    }

    /**
     * 固定盐加密加定义盐二次加密密码 (确保入库密码安全性)双重加密
     * @param inputPass
     * @param saltDB
     * @return
     */
    public static String md5Salt2(String inputPass, String saltDB) {
        String formPass = md5Salt(inputPass);
        String dbPass = md5Salt(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println("md5加密：" + md5("123456"));
        System.out.println("固定盐md5加密：" + md5Salt("123456"));
        System.out.println("自定义盐md5加密：" + md5Salt("123456",salt));
        System.out.println("固定盐加密加定义盐二次加密密码：" + md5Salt2("123456",salt));
    }

}

