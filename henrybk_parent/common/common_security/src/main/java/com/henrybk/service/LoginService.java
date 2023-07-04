package com.henrybk.service;

import com.henrybk.enums.LoginStatusEnum;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    //更新错误次数
    void updatePwdErrNum(String username,boolean flag);
    //登录日志
    void recordLoginLog(HttpServletRequest request, String username, LoginStatusEnum status, String message);

}
