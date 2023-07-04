package com.henrybk.system.service.impl;

import com.henrybk.custom.CustomUser;
import com.henrybk.enums.StatusEnum;
import com.henrybk.model.sys.SysUser;
import com.henrybk.system.service.SysMenuService;
import com.henrybk.system.service.SysUserService;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 用户认证实现类
 * @author Henry
 * @since 2023-06-05
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 根据用户名获取用户对象
     * @param username 用户名
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username.toUpperCase());
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名密码错误！");
        }

        if(sysUser.getStatus() == StatusEnum.FAIL) {
            throw new LockedException("账号已停用");
        }

        //根据userid查询操作权限数据
        List<String> userPermsList = sysMenuService.getUserButtonList(sysUser.getId());
        //转换security要求格式数据
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm:userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorities);
    }
}
