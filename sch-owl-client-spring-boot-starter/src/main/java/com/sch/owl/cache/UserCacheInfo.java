package com.sch.owl.cache;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserCacheInfo {

    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 工作手机号码
     */
    private String workPhone;

    /**
     * 座席号主键
     */
    private Long callId;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 在职状态（0：在职，1：离职）
     */
    private Integer workStatus;

    /**
     * 企业微信绑定状态 0：未绑定  1：绑定
     */
    private Integer qwStatus;

    /**
     * 企业微信用户ID
     */
    private String qwUserId;

    /**
     * 岗位编码
     */
    private List<UserPost> posts;

    public List<String> getPostCodes() {
        if (CollectionUtils.isEmpty(this.posts)) {
            return Collections.emptyList();
        }
        return this.posts.stream().map(UserPost::getCode).collect(Collectors.toList());
    }

    @Data
    public static class UserPost {

        private String code;

        private String name;

    }
}
