package com.sch.owl.infrastructure.repository.auth;

import com.sch.owl.domain.repository.auth.PasswordRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class PasswordRepositoryImpl implements PasswordRepository {

    @Override
    public Integer getRetryCount(String failKey) {
        return 0;
    }

    @Override
    public void setRetryCount(String failKey, Integer retryCount, Integer lockTime, TimeUnit timeUnit) {

    }

    @Override
    public boolean hasFailKey(String failKey) {
        return false;
    }

    @Override
    public void deleteFailKey(String failKey) {

    }
}
