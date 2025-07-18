package com.sch.owl.application.reponse.auth;

import lombok.Data;

/**
 * 登陆用户信息
 */
@Data
public class LoginUserResp {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

}
