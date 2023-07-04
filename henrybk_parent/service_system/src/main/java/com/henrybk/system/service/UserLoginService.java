package com.henrybk.system.service;

import com.henrybk.vo.vo.sys.LoginVo;

import javax.validation.Valid;

/**
 * @description 后台管理系统登录 服务类
 * @author Henry
 * @since 2023-05-16
 */
public interface UserLoginService {
    String login(@Valid LoginVo loginVo);
}
