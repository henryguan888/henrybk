package com.henrybk.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.henrybk.model.sys.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.vo.query.sys.SysOperLogQueryVo;

/**
 * @description 操作日志记录 服务类
 * @author Henry
 * @since 2023-05-26
 */
public interface SysOperLogService extends IService<SysOperLog> {

    Page<SysOperLog> getPageList(Integer pageNum, Integer pageSize, SysOperLogQueryVo sysOperLogQueryVo);
}
