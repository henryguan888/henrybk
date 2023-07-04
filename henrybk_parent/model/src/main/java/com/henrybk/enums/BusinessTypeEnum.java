package com.henrybk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

    OTHER(0, "其它"),
    INSERT(1, "新增"),
    UPDATE(2, "修改"),
    UPDATE_STATUS(3, "更新状态"),
    DELETE(4, "删除"),
    BATCH_DEL(5, "批量删除"),
    UPLOAD(6, "导入"),
    DOWNLOAD(7, "导出"),
    ASSIGN(11, "授权"),
    CANCEL_ASSIGN(12, "取消授权"),
    BATCH_CANCEL_ASSIGN(13, "批量取消授权"),
    FORCE(80, "强退"),
    CLEAN(90, "清空数据"),

    ;

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}