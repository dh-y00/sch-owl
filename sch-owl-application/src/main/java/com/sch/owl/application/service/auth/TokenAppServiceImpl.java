package com.sch.owl.application.service.auth;

import com.sch.owl.domain.service.auth.TokenDomain;
import com.sch.owl.model.UserDetail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenAppServiceImpl implements TokenAppService {

    @Autowired
    private TokenDomain tokenDomain;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetail getLoginUserByToken(String token) {
        if (StringUtils.isNotBlank(token))
        {

            try
            {
                return tokenDomain.getLoginUserByToken(token);
            }
            catch (Exception e)
            {
                logger.error("获取用户信息异常'{}'", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void removeByUserName(String userName) {
        if(StringUtils.isBlank(userName)) {
            return ;
        }
        tokenDomain.removeByUserName(userName);
    }

    @Override
    public void verifyUserDetail(UserDetail userDetail) {

        long expireTime = userDetail.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= TokenDomain.MILLIS_MINUTE_TEN)
        {
            tokenDomain.refreshToken(userDetail);
        }
    }

    @Override
    public String getHeaderTokenName() {
        return tokenDomain.getHeaderTokenName();
    }

}
