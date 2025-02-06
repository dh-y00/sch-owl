package com.sch.owl.config.security;

import com.alibaba.fastjson2.JSON;
import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.rdrk.upms.BaseResponse;
import com.rdrk.upms.config.factory.AsyncFactory;
import com.rdrk.upms.config.factory.AsyncManager;
import com.rdrk.upms.constant.Constants;
import com.rdrk.upms.model.LoginUser;
import com.rdrk.upms.service.TokenService;
import com.rdrk.upms.token.TokenDeal;
import com.rdrk.upms.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author ruoyi
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenDeal tokenDeal;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser(tokenDeal.getToken(request));
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(BaseResponse.success("退出成功")));
    }
}
