package com.sch.owl.controller;

import com.sch.owl.application.reponse.auth.LoginUserResp;
import com.sch.owl.application.request.auth.LoginCommand;
import com.sch.owl.application.service.auth.LoginAppService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class LoginController {

    @Autowired
    private LoginAppService loginAppService;

    /**
     * 登录方法
     *
     * @param loginCommand 登录信息
     * @return jwt
     */
    @PostMapping("/login")
    @ApiOperation(nickname = "", value = "登录接口",notes = "账号密码登录")
    public String login(@RequestBody LoginCommand loginCommand) {
        return loginAppService.login(loginCommand);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public LoginUserResp getInfo() {
        return loginAppService.loginUser();
    }

}
