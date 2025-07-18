package com.sch.owl.application.service.auth;

import com.sch.owl.model.UserDetail;

/**
 * token 相关 app 服务
 */
public interface TokenAppService {

    /**
     * 根据 token 信息获取用户信息
     * @param token token 信息
     * @return 返回用户信息
     */
    UserDetail getLoginUserByToken(String token);

    /**
     * 移除登陆信息根据用户名
     * @param userName 用户名
     */
    void removeByUserName(String userName);

    /**
     * 验证当前用户信息
     *
     * @param userDetail 当前登陆信息
     */
    void verifyUserDetail(UserDetail userDetail);

    /**
     * 获取请求头中放token的姓名
     * @return token 名称
     */
    String getHeaderTokenName();
}
