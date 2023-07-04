package com.henrybk.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henrybk.enums.LoginStatusEnum;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.sys.SysLoginLog;
import com.henrybk.model.sys.SysUser;
import com.henrybk.service.LoginService;
import com.henrybk.system.mapper.SysLoginLogMapper;
import com.henrybk.system.service.SysLoginLogService;
import com.henrybk.system.service.SysUserService;
import com.henrybk.utils.AddressUtil;
import com.henrybk.utils.IpUtil;
import com.henrybk.vo.query.sys.SysLoginLogQueryVo;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @description 登录日志表 服务实现类
 * @author Henry
 * @since 2023-05-20
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService, LoginService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public void updatePwdErrNum(String username,boolean flag) {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) return;
        if (!flag) {
            sysUser.setPwdErrNum(sysUser.getPwdErrNum()+1);
            if (sysUser.getPwdErrNum() >= 5) {
                sysUser.setStatus(StatusEnum.FAIL);
            }
        } else {
            sysUser.setPwdErrNum(0);
        }

        sysUserService.updateById(sysUser);
    }

    /**
     * 记录登录日志
     * @param request
     * @param username
     * @param status
     * @param message
     */
    @Override
    public void recordLoginLog(HttpServletRequest request, String username, LoginStatusEnum status, String message) {

        UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLoginId(username);
        sysLoginLog.setSessionId(String.valueOf(System.currentTimeMillis()));
        sysLoginLog.setLoginIp(IpUtil.getIpAddr(request));
        sysLoginLog.setLoginAddress(AddressUtil.getRealAddressByIP(IpUtil.getIpAddr(request)));
        sysLoginLog.setBrowser(ua.getBrowser().toString());
        sysLoginLog.setOperatingSystem(ua.getOperatingSystem().toString());
        sysLoginLog.setLoginTime(new Date());
        sysLoginLog.setStatus(status);
        sysLoginLog.setMessage(message);

        baseMapper.insert(sysLoginLog);

        SysUser sysUser = sysUserService.getByUsername(username);
        if(sysUser == null) return;
        sysUser.setLoginIp(IpUtil.getIpAddr(request));
        sysUser.setLoginDate(new Date());
        sysUserService.updateById(sysUser);
    }

    @Override
    public Page<SysLoginLog> getSysLoginLogPage(Integer pageNum, Integer pageSize, SysLoginLogQueryVo sysLoginLogQueryVo) {
        Page<SysLoginLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(sysLoginLogQueryVo.getLoginId()), SysLoginLog::getLoginId, sysLoginLogQueryVo.getLoginId());
        queryWrapper.like(StringUtils.isNotEmpty(sysLoginLogQueryVo.getLoginIp()), SysLoginLog::getLoginIp, sysLoginLogQueryVo.getLoginIp());
        queryWrapper.eq(sysLoginLogQueryVo.getStatus() != null, SysLoginLog::getStatus, sysLoginLogQueryVo.getStatus());
        queryWrapper.gt(StringUtils.isNotEmpty(sysLoginLogQueryVo.getCreateTimeBegin()), SysLoginLog::getLoginTime, sysLoginLogQueryVo.getCreateTimeBegin());
        queryWrapper.lt(StringUtils.isNotEmpty(sysLoginLogQueryVo.getCreateTimeEnd()), SysLoginLog::getLoginTime, sysLoginLogQueryVo.getCreateTimeEnd());
        queryWrapper.orderByDesc(SysLoginLog::getLoginTime);
        return baseMapper.selectPage(page, queryWrapper);
    }
}
