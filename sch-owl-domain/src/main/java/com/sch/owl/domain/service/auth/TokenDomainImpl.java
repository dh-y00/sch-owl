package com.sch.owl.domain.service.auth;

import com.sch.owl.common.properties.TokenProperties;
import com.sch.owl.constant.Constants;
import com.sch.owl.domain.constants.auth.TokenConstants;
import com.sch.owl.domain.model.auth.TokenEntity;
import com.sch.owl.domain.repository.auth.TokenRepository;
import com.sch.owl.model.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenDomainImpl implements TokenDomain {

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    @Resource
    private TokenRepository tokenRepository;

    @Resource
    private TokenProperties tokenProperties;

    @Override
    public String createToken(UserDetail userDetail) {
        String userName = userDetail.getUsername();
        refreshToken(userDetail);
        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenConstants.LOGIN_USER_KEY, userName);
        return createToken( claims);
    }

    @Override
    public void refreshToken(UserDetail userDetail) {
        userDetail.setLoginTime(System.currentTimeMillis());
        userDetail.setExpireTime(userDetail.getLoginTime() + tokenProperties.getExpireTime() * MILLIS_MINUTE);
        String tokenKey = getTokenKey(userDetail.getUsername());
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setTokenKey(tokenKey);
        tokenEntity.setUserDetail(userDetail);
        tokenEntity.setExpireTime(tokenProperties.getExpireTime());
        tokenEntity.setTimeUnit(TimeUnit.MINUTES);
        tokenRepository.saveToken(tokenEntity);
    }

    @Override
    public UserDetail getLoginUserByToken(String token) {
        Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
        String tokenKey = getTokenKey(uuid);
        return tokenRepository.getTokenByTokenKey(tokenKey);
    }

    @Override
    public void removeByUserName(String userName) {
        String tokenKey = getTokenKey(userName);
        tokenRepository.delByTokenKey(tokenKey);
    }

    @Override
    public String getHeaderTokenName() {
        return tokenProperties.getHeader();
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret()).compact();
        return token;
    }

    /**
     * 获取到 token 的key
     * @param uuid uuid
     * @return 对应的key
     */
    private String getTokenKey(String uuid)
    {
        return TokenConstants.TOKEN_KEY + uuid;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }
}
