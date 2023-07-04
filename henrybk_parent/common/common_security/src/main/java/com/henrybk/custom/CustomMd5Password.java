package com.henrybk.custom;

import com.henrybk.config.PropertiesConfig;
import com.henrybk.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
/**
 * @description 自定义密码组件
 * @author Henry
 * @since 2023-06-05
 */
@Component
public class CustomMd5Password implements PasswordEncoder {

    @Resource
    private PropertiesConfig propertiesConfig;

    public String encode(CharSequence rawPassword) {
        String salt = propertiesConfig.getPasswordSalt();
        return MD5Util.md5Salt(rawPassword.toString(),salt);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String salt = propertiesConfig.getPasswordSalt();
        return encodedPassword.equals(MD5Util.md5Salt(rawPassword.toString(),salt));
    }
}
