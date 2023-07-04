package com.henrybk.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henrybk.enums.BusinessTypeEnum;
import com.henrybk.enums.OperStatusEnum;
import com.henrybk.enums.OperatorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author Henry
 * @since 2023-05-26
 */
@Data
@ApiModel(description = "操作日志实体")
@TableName("sys_oper_log")
public class SysOperLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "日志主键")
    @TableId(value = "oper_id", type = IdType.AUTO)
    private Integer operId;

    @ApiModelProperty(value = "模块标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "业务类型")
    @TableField("business_type")
    private BusinessTypeEnum businessType;

    @ApiModelProperty(value = "方法名称")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "请求方式")
    @TableField("request_method")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    @TableField("operator_type")
    private OperatorTypeEnum operatorType;

    @ApiModelProperty(value = "操作人员")
    @TableField("oper_name")
    private String operName;

    @ApiModelProperty(value = "部门名称")
    @TableField("dept_name")
    private String deptName;

    @ApiModelProperty(value = "请求URL")
    @TableField("oper_url")
    private String operUrl;

    @ApiModelProperty(value = "主机地址")
    @TableField("oper_ip")
    private String operIp;

    @ApiModelProperty(value = "操作地点")
    @TableField("oper_location")
    private String operLocation;

    @ApiModelProperty(value = "请求参数")
    @TableField("oper_param")
    private String operParam;

    @ApiModelProperty(value = "返回参数")
    @TableField("json_result")
    private String jsonResult;

    @ApiModelProperty(value = "操作状态（0异常 1正常）")
    @TableField("status")
    private OperStatusEnum status;

    @ApiModelProperty(value = "错误消息")
    @TableField("error_msg")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间")
    @TableField("oper_time")
    private Date operTime;

    @ApiModelProperty(value = "消耗时间")
    @TableField("cost_time")
    private Long costTime;

}
