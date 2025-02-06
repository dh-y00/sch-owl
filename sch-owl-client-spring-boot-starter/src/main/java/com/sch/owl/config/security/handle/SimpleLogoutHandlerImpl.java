package com.sch.owl.config.security.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 由于业务系统本质上没有退出登录这一说，这边给个假的demo 要退出登录 请走权限系统 返回成功
 * 
 * @author yaodonghu
 */
public class SimpleLogoutHandlerImpl implements LogoutSuccessHandler
{

    private Logger logger = LoggerFactory.getLogger("upms-security");

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        logger.warn("退出登录请移步权限系统");
    }
}
