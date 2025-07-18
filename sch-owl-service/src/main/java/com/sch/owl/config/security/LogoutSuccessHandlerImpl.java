package com.sch.owl.config.security;

import com.alibaba.fastjson2.JSON;
import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.BaseResponse;
import com.sch.owl.application.service.auth.TokenAppService;
import com.sch.owl.model.UserDetail;
import com.sch.owl.token.TokenDeal;
import com.sch.owl.utils.ServletUtils;
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
    private TokenAppService tokenAppService;

    @Autowired
    private TokenDeal tokenDeal;

    /**
     * 退出处理
     *
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        UserDetail userDetail = tokenAppService.getLoginUserByToken(tokenDeal.getToken(request));
        if (StringUtils.isNotNull(userDetail))
        {
            // 删除用户缓存记录
            tokenAppService.removeByUserName(userDetail.getUsername());
            // todo 记录用户退出日志
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(BaseResponse.success("退出成功")));
    }
}
