package com.sch.owl.config.security;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.rdrk.upms.constant.UserStatus;
import com.rdrk.upms.exception.ServiceException;
import com.rdrk.upms.model.LoginUser;
import com.rdrk.upms.model.LoginUserInfo;
import com.rdrk.upms.service.PasswordService;
import com.rdrk.upms.service.db.UpmsUserDbService;
import com.rdrk.upms.service.user.PermissionService;
import com.rdrk.upms.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UpmsUserDbService userService;
    
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        LoginUserInfo loginUserInfo = userService.queryUserByUserName(username);
        if (StringUtils.isNull(loginUserInfo))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        }
        else if (UserStatus.DELETED.getCode().equals(loginUserInfo.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        }
        else if (UserStatus.DISABLE.getCode().equals(loginUserInfo.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        passwordService.validate(loginUserInfo);

        return createLoginUser(loginUserInfo);
    }

    public UserDetails createLoginUser(LoginUserInfo user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
