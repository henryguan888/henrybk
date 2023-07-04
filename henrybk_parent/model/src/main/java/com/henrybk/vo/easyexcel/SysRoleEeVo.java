package com.henrybk.vo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 角色信息Excel对象
 * @author Henry
 * @since 2023-05-18
 */
@Data
public class SysRoleEeVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ExcelProperty("角色名称")
    private String roleName;

    @ExcelProperty("角色编码")
    private String roleCode;

    @ExcelProperty("显示顺序")
    private Integer orderNum;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ExcelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
