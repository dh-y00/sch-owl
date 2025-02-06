package com.sch.owl.config.security.handle;

import com.alibaba.fastjson2.JSON;
import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.rdrk.rsf.framework.web.BaseResponse;
import com.sch.owl.constant.HttpStatus;
import com.sch.owl.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author ruoyi
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(BaseResponse.error(code, msg)));
    }
}
