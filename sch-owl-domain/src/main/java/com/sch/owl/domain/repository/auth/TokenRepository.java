package com.sch.owl.domain.repository.auth;

import com.sch.owl.domain.model.auth.TokenEntity;
import com.sch.owl.model.UserDetail;

public interface TokenRepository {

    /**
     * 保存token
     */
    void saveToken(TokenEntity tokenEntity);

    /**
     * 根据tokenKey获取token
     *
     * @param tokenKey tokenKey
     * @return  token
     */
    UserDetail getTokenByTokenKey(String tokenKey);

    /**
     * 删除token
     *
     * @param tokenKey tokenKey
     */
    void delByTokenKey(String tokenKey);
}
