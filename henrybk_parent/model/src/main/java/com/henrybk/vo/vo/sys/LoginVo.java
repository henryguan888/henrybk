package com.henrybk.vo.vo.sys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @description 登录对象
 * @author Henry
 * @since 2023-05-15
 */
@Data
@ApiModel(description = "登录对象")
public class LoginVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
   // @NotEmpty(message = "验证码不能为空")
    private String code;
}
