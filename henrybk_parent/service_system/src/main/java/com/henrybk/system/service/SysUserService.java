package com.henrybk.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysUser;
import com.henrybk.vo.query.sys.SysUserQueryVo;
import com.henrybk.vo.query.sys.UpdatePwdQueryVo;
import com.henrybk.vo.vo.sys.SysUserVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description 用户信息表 服务类
 * @author Henry
 * @since 2023-05-15
 */
public interface SysUserService extends IService<SysUser> {

    Map<String,Object> getInfo(String token);

    SysUser getByUsername(String username);

    IPage<SysUserVo> getUserInfoPage(Integer pageNum, Integer pageSize, SysUserQueryVo sysUserQueryVo);

    void updateStatus(Long id, Integer status);

    void upload(MultipartFile file);

    void download(HttpServletResponse response);

    void restPassword(Long userId);

    void saveUser(SysUser sysUser);

    void updateUser(SysUser sysUser);

    SysUserVo getUserInfo(String token);

    void updatePwd(UpdatePwdQueryVo updatePwdQueryVo, HttpServletRequest request);

    Map<String, Object> getSystemData();

    List<Map<String, Object>> getUserByRole();

    Map<String, Object> getLoginAndOperData(String searchDate);
}
