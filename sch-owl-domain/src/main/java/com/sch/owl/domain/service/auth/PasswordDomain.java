package com.sch.owl.domain.service.auth;

import com.sch.owl.domain.repository.org.UserEntity;

public interface PasswordDomain {

    /**
     * 验证 登陆的密码是否正确
     *
     * @param userInfo 用户名对应的用户
     */
    void validate(UserEntity userInfo);
}
