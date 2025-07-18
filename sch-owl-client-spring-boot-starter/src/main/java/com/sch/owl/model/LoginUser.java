package com.sch.owl.model;

import lombok.Data;

/**
 * 登陆的用户信息
 * 
 * @author Ydh
 */
@Data
public class LoginUser {

    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户头像
     */
    private String avatar;

}
