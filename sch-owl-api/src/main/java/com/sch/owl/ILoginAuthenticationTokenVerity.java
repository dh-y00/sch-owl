package com.sch.owl;


import javax.servlet.http.HttpServletRequest;

/**
 * 登录权限验证
 */
public interface ILoginAuthenticationTokenVerity {

    void verity(String token, HttpServletRequest request);

}
