package com.sch.owl.domain.service.org;

import com.sch.owl.domain.dto.org.UserQuery;
import com.sch.owl.domain.repository.org.UserEntity;

public interface UserDomain {

    /**
     * 保存用户
     * @param userEntity 用户实体
     * @return 返回保存的用户ID
     */
    Long saveUser(UserEntity userEntity);

    /**
     * 验证当前用户信息是否有效
     * @param userEntity 用户实体信息
     */
    void checkUser(UserEntity userEntity);

    /**
     * 保存用户信息
     * @param userEntity 用户实体
     */
    void updateUser(UserEntity userEntity);

    /**
     * 获取到用户信息
     * @param userQuery 用户信息查询条件
     * @return 返回用户实体信息
     */
    UserEntity getUserInfo(UserQuery userQuery);

}
