package com.henrybk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henrybk.model.sys.SysOperLog;

/**
 * @description 异步操作日志记录接口
 * @author Henry
 * @since 2023-05-22
 */
public interface AsyncOperLogService extends IService<SysOperLog> {

    void saveSysOperLog(SysOperLog sysOperLog);
}
