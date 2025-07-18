package com.sch.owl.infrastructure.repository.auth;

import com.sch.owl.domain.model.auth.TokenEntity;
import com.sch.owl.domain.repository.auth.TokenRepository;
import com.sch.owl.model.UserDetail;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    @Override
    public void saveToken(TokenEntity tokenEntity) {

    }

    @Override
    public UserDetail getTokenByTokenKey(String tokenKey) {
        return null;
    }

    @Override
    public void delByTokenKey(String tokenKey) {

    }
}
