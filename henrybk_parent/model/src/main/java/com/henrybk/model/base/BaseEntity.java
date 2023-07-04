package com.henrybk.model.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 基础实体类
 * @author Henry
 * @since 2023-03-15
 */
@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除(0未删除 1已删除)")
    @TableLogic //表示该字段是逻辑删除字段
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    private Integer isDeleted;

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false) //非表字段
    private Map<String,Object> param = new HashMap<>();
}
