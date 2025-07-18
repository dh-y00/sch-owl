package com.sch.owl.domain.service.org;

import com.sch.owl.domain.dto.org.UserQuery;
import com.sch.owl.domain.repository.org.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserDomainImpl implements UserDomain {

    @Override
    public Long saveUser(UserEntity userEntity) {
        return 0L;
    }

    @Override
    public void checkUser(UserEntity userEntity) {

    }

    @Override
    public void updateUser(UserEntity userEntity) {

    }

    @Override
    public UserEntity getUserInfo(UserQuery userQuery) {
        return null;
    }
}
