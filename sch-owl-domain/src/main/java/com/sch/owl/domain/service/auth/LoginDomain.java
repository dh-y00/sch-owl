package com.sch.owl.domain.service.auth;

import com.sch.owl.domain.dto.auth.LoginDTO;

public interface LoginDomain {

    /**
     * 登陆
     * @param loginDTO 登陆所需要参数
     * @return 登陆成功返回  token
     */
    String login(LoginDTO loginDTO);

}
