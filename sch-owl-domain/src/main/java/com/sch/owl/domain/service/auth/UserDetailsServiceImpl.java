package com.sch.owl.domain.service.auth;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.domain.dto.org.UserQuery;
import com.sch.owl.domain.repository.org.UserEntity;
import com.sch.owl.domain.service.org.UserDomain;
import com.sch.owl.exception.ServiceException;
import com.sch.owl.model.LoginUser;
import com.sch.owl.model.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * security 用户验证处理
 *
 * @author yaodonghu
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    private UserDomain userDomain;
    
    @Autowired
    private PasswordDomain passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername(username);
        UserEntity userInfo = userDomain.getUserInfo(userQuery);
        if (StringUtils.isNull(userInfo))
        {
            log.info("登录用户：{} 不存在.", username);
            // todo 增加错误文本
            throw new ServiceException("");
        }
        userDomain.checkUser(userInfo);

        passwordService.validate(userInfo);

        return createLoginUser(userInfo);
    }

    /**
     * 创建登陆信息
     * @param userInfo 用户实体
     * @return 登陆用户信息
     */
    public UserDetails createLoginUser(UserEntity userInfo)
    {
        UserDetail userDetail = new UserDetail();
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(userInfo.getId());
        loginUser.setUserName(userInfo.getUserName());
        loginUser.setNickName(userInfo.getNickName());
        loginUser.setEmail(userInfo.getEmail());
        loginUser.setPhoneNumber(userInfo.getPhone());
        loginUser.setAvatar(userInfo.getAvatar());
        return userDetail;
    }
}
