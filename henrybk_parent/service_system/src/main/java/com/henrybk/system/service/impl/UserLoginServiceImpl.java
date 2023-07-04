package com.henrybk.system.service.impl;

import com.henrybk.config.PropertiesConfig;
import com.henrybk.enums.ResultEnum;
import com.henrybk.enums.StatusEnum;
import com.henrybk.exception.BusinessException;
import com.henrybk.model.sys.SysUser;
import com.henrybk.system.service.SysUserService;
import com.henrybk.system.service.UserLoginService;
import com.henrybk.utils.JwtUtil;
import com.henrybk.utils.MD5Util;
import com.henrybk.vo.vo.sys.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description 后台管理系统登录 服务实现类
 * @author Henry
 * @since 2023-05-16
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private PropertiesConfig propertiesConfig;


    @Override
    public String login(@Valid LoginVo loginVo) {
        // 获取盐值
        String salt = propertiesConfig.getPasswordSalt();

        //校验用户名和密码
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername().toUpperCase());

        if (sysUser == null) {
            throw new BusinessException(ResultEnum.ACCOUNT_PASSWORD_ERROR);
        }

        if (!StringUtils.equals(sysUser.getPassword(), MD5Util.md5Salt(loginVo.getPassword(),salt))) {
            //更新密码错误次数，次数大于4次，锁定账户
            sysUser.setPwdErrNum(sysUser.getPwdErrNum()+1);
            if (sysUser.getPwdErrNum() > 4) {
                sysUser.setStatus(StatusEnum.FAIL);
            }
            sysUserService.updateById(sysUser);

            throw new BusinessException(ResultEnum.ACCOUNT_PASSWORD_ERROR);
        }

        if (sysUser.getStatus() == StatusEnum.FAIL) {
            throw new BusinessException(ResultEnum.ACCOUNT_LOCK);
        }

        //登录成功，重置密码错误次数
        sysUser.setPwdErrNum(0);
        sysUserService.updateById(sysUser);
        return JwtUtil.createToken(sysUser.getId(), sysUser.getUsername());

    }

}
