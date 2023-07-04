package com.henrybk.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrybk.constants.TypeCode;
import com.henrybk.custom.CustomUser;
import com.henrybk.enums.LoginStatusEnum;
import com.henrybk.enums.ResultEnum;
import com.henrybk.result.R;
import com.henrybk.service.LoginService;
import com.henrybk.utils.JwtUtil;
import com.henrybk.utils.ResponseUtil;
import com.henrybk.vo.vo.sys.LoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 登录过滤器
 * @author Henry
 * @since 2023-06-05
 */

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private LoginService loginService;

    private String username;


    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate,LoginService loginService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/userLogin/login","POST"));
        this.redisTemplate = redisTemplate;
        this.loginService = loginService;
    }

    /**
     * 登录认证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);
            this.username = loginVo.getUsername().toUpperCase();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername().toUpperCase(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        //获取认证对象
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        //生成token
        String token = JwtUtil.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());
        //保存权限数据
        redisTemplate.opsForValue().set(TypeCode.LOGIN_PERMS_KEY + customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        loginService.updatePwdErrNum(this.username,true);
        loginService.recordLoginLog(request,customUser.getSysUser().getUsername(), LoginStatusEnum.SUCCESS,ResultEnum.LOGIN_SUCCESS.getMsg());
        //返回token
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, R.ok().data(map));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map<Class<? extends Throwable>, ResultEnum> exceptionMappings = new HashMap<>();
        exceptionMappings.put(AccountExpiredException.class, ResultEnum.ACCOUNT_EXPIRED);
        exceptionMappings.put(BadCredentialsException.class, ResultEnum.ACCOUNT_PASSWORD_ERROR);
        exceptionMappings.put(CredentialsExpiredException.class, ResultEnum.ACCOUNT_PASSWORD_EXPIRED);
        exceptionMappings.put(DisabledException.class, ResultEnum.ACCOUNT_DISABLED);
        exceptionMappings.put(LockedException.class, ResultEnum.ACCOUNT_LOCK);
        exceptionMappings.put(InternalAuthenticationServiceException.class, ResultEnum.ACCOUNT_NUll);

        ResultEnum resultEnum = exceptionMappings.getOrDefault(e.getClass(), ResultEnum.SYSTEM_ERROR);
        if(resultEnum == ResultEnum.ACCOUNT_PASSWORD_ERROR) {
            loginService.updatePwdErrNum(this.username, false);
        }
        loginService.recordLoginLog(request, this.username, LoginStatusEnum.FAIL, resultEnum.getMsg());
        ResponseUtil.out(response, R.error(resultEnum));
    }

}
