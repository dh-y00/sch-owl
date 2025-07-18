package com.sch.owl.application.request.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登陆参数
 *
 * @author Ydh
 */
@Data
@ApiModel(value="用户登录表单对象",description="用户登录表单对象")
public class LoginCommand {

    @NotBlank(message = "用户名不可以为空")
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    @NotBlank(message = "密码不可以为空")
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

}
