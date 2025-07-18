package com.sch.owl.domain.service.auth;

import com.sch.owl.common.properties.UserPasswordProperties;
import com.sch.owl.config.security.context.AuthenticationContextHolder;
import com.sch.owl.domain.repository.auth.PasswordRepository;
import com.sch.owl.domain.repository.org.UserEntity;
import com.sch.owl.exception.UserPasswordNotMatchException;
import com.sch.owl.exception.UserPasswordRetryLimitExceedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PasswordDomainImpl implements PasswordDomain {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private UserPasswordProperties userPasswordProperties;

    /**
     * 登录账户密码错误次数 redis key
     */
    static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    @Override
    public void validate(UserEntity userInfo) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();
        Integer retryCount = passwordRepository.getRetryCount(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        Integer maxRetryCount = userPasswordProperties.getMaxRetryCount();

        Integer lockTime = userPasswordProperties.getLockTime();

        if (retryCount >= maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(userInfo.getPassword(), password)) {
            retryCount = retryCount + 1;
            passwordRepository.setRetryCount(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }

    /**
     * 密码匹配
     * @param encodedPassword 数据库里面存入的加入密码
     * @param rawPassword 此次登陆的密码
     * @return true-正确 false-失败
     */
    public boolean matches(String encodedPassword, String rawPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 清楚登陆失败的记录
     *
     * @param loginName 登陆的用户名
     */
    public void clearLoginRecordCache(String loginName)
    {
        if (passwordRepository.hasFailKey(getCacheKey(loginName)))
        {
            passwordRepository.deleteFailKey(getCacheKey(loginName));
        }
    }


    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return PWD_ERR_CNT_KEY + username;
    }
}
