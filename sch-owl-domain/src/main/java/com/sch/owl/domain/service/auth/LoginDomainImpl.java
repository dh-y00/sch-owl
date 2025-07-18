package com.sch.owl.domain.service.auth;

import com.sch.owl.config.security.context.AuthenticationContextHolder;
import com.sch.owl.domain.constants.auth.LoginConstants;
import com.sch.owl.domain.dto.auth.LoginDTO;
import com.sch.owl.domain.dto.org.UserQuery;
import com.sch.owl.domain.repository.org.UserEntity;
import com.sch.owl.domain.service.org.UserDomain;
import com.sch.owl.exception.ServiceException;
import com.sch.owl.exception.UserNotExistsException;
import com.sch.owl.exception.UserPasswordNotMatchException;
import com.sch.owl.model.UserDetail;
import com.sch.owl.utils.AddressUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class LoginDomainImpl implements LoginDomain {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDomain userDomain;

    @Resource
    private TokenDomain tokenDomain;

    @Override
    public String login(LoginDTO loginDTO) {
        loginPreCheck(loginDTO.getUsername(), loginDTO.getPassword());
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername(loginDTO.getUsername());
        UserEntity userInfo = userDomain.getUserInfo(userQuery);
        Authentication authenticate;
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            AuthenticationContextHolder.setContext(authenticationToken);
            authenticate = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e) {
            if (e instanceof BadCredentialsException)
            {
                // todo 日志记录
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                // 日志记录
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }finally {
            AuthenticationContextHolder.clearContext();
        }
        userInfo.setLatestLoginIp(AddressUtils.getIpAddr());
        userInfo.setLatestLoginTime(LocalDateTime.now());
        userDomain.updateUser(userInfo);
        // 生成token
        UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
        return tokenDomain.createToken(userDetail);
    }


    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            // todo 这边需要增加日志记录
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < LoginConstants.PASSWORD_MIN_LENGTH
                || password.length() > LoginConstants.PASSWORD_MAX_LENGTH)
        {
            // todo 日志
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < LoginConstants.USERNAME_MIN_LENGTH
                || username.length() > LoginConstants.USERNAME_MAX_LENGTH)
        {
            // todo 日志
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
    }

}
