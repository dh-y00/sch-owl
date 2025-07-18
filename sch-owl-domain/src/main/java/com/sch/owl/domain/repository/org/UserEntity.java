package com.sch.owl.domain.repository.org;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0-无效 1- 有效
     */
    private String status;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 最新登陆时间
     */
    private LocalDateTime latestLoginTime;

    /**
     * 最新登陆ip
     */
    private String latestLoginIp;

}
