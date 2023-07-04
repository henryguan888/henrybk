package com.henrybk.result;

import com.henrybk.enums.ResultEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 统一接口返回类
 * @author Henry
 * @since 2023-03-15
 */
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private R() {

    }
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMessage(ResultEnum.SUCCESS.getMsg());
        return r;
    }

    public static R ok(Integer code,String message) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultEnum.ERROR.getCode());
        r.setMessage(ResultEnum.ERROR.getMsg());
        return r;
    }
    public static R error(ResultEnum resultEnum) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMsg());
        return r;
    }

    public static R error(Integer code,String message) {
        R r = new R();
        r.setSuccess(true);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
