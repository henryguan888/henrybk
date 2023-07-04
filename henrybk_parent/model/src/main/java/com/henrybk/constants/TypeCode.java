package com.henrybk.constants;

/**
 * @description 类型编码值常量
 * @author Henry
 * @since 2023-05-17
 */

public interface TypeCode {

    /**
     * 否
     */
    Integer FLAG_ZERO = 0;
    /**
     * 是
     */
    Integer FLAG_ONE = 1;

    /**
     * 查询ip地理位置
     */
    String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip={ip}&json=true";

    /**
     * 登录打印信息
     */
    String LOGIN_INFO = "用户输入用户名：[%s]；用户输入密码：[%s]；用户加密后密码：[%s]；系统返回密码：[%s]";

    /**
     * 用户执行该方法开始时间
     */
    String SYS_OPER_BEGIN_TIME = "SYS_OPER_BEGIN_TIME";


    /**
     * redis登录权限数据目录
     */
    String LOGIN_PERMS_KEY = "henrybk:login:perms:";

    /**
     * redis数据字典
     */
    String DATA_DICT_TYPE_KEY = "henrybk:dict:dictData";


    String ENUMS_PATH = "com.henrybk.enums.";

    /********************sys_config表key*****************************/

    /**
     * 超级管理员账号
     */
    String SUPER_ADMIN = "SUPER_ADMIN";
    /**
     * 初始化密码
     */
    String INIT_PASSWORD = "INIT_PASSWORD";


    /***************************************************************/
}
