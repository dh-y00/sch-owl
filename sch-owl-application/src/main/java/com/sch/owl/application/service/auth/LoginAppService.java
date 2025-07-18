package com.sch.owl.application.service.auth;

import com.sch.owl.application.reponse.auth.LoginUserResp;
import com.sch.owl.application.request.auth.LoginCommand;

public interface LoginAppService {

    /**
     * 登录
     *
     * @param loginCommand 登陆参数
     * @return 返回 jwt
     */
    String login(LoginCommand loginCommand);


    /**
     * 获取到当前登陆人的信息
     */
    LoginUserResp loginUser();
}
