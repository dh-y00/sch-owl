package com.sch.owl.domain.service.auth;

import com.sch.owl.model.UserDetail;

public interface TokenDomain {

    long MILLIS_SECOND = 1000;

    long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 创建 token
     * @return 返回 token 字符串
     */
    String createToken(UserDetail userDetail);

    /**
     * 刷新 token
     */
    void refreshToken(UserDetail userDetail);

    /**
     * 获取登录用户
     * @param token  token
     * @return 登录用户
     */
    UserDetail getLoginUserByToken(String token);

    /**
     * 移除 token 信息根据用户信息
     * @param userName 用户名
     */
    void removeByUserName(String userName);

    /**
     * 获取请求头中放token的姓名
     * @return token 名称
     */
    String getHeaderTokenName();
}
