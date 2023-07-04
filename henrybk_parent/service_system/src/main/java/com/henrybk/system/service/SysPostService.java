package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysPost;
import com.henrybk.vo.query.sys.SysPostQueryVo;

import javax.servlet.http.HttpServletResponse;


/**
 * @description 岗位信息表 服务类
 * @author Henry
 * @since 2023-05-20
 */
public interface SysPostService extends IService<SysPost> {

    Page<SysPost> getSysPostPage(Integer pageNum, Integer pageSize, SysPostQueryVo sysPostQueryVo);

    void updateStatus(Long id, Integer status);

    void download(HttpServletResponse response);
}
