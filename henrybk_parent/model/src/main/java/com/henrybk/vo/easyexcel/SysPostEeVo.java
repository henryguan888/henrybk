package com.henrybk.vo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 岗位信息Excel对象
 * @author Henry
 * @since 2023-05-26
 */
@Data
public class SysPostEeVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ExcelProperty("岗位编码")
    private String postCode;

    @ExcelProperty("岗位名称")
    private String name;

    @ExcelProperty("显示顺序")
    private Integer orderNum;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("备注")
    private String remark;
}
