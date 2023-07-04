package com.henrybk.vo.query.sys;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
/*
 * @description 修改密码请求实体
 * @author Henry
 * @since 2023-06-21
 */
@Data
public class UpdatePwdQueryVo {

    @NotEmpty(message = "旧密码不能为空")
    private String oldPwd;

    @NotEmpty(message = "新密码不能为空")
    private String newPwd;

    @NotEmpty(message = "确认密码不能为空")
    private String confirmPwd;
}
