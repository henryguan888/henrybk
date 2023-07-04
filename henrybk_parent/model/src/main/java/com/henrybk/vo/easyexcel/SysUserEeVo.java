package com.henrybk.vo.easyexcel;


import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 用户信息Excel对象
 * @author Henry
 * @since 2023-05-15
 */
@Data
public class SysUserEeVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("岗位名称")
    private String postName;

    @ExcelProperty("用户邮箱")
    private String email;

    @ExcelProperty("手机号码")
    private String phone;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ExcelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
