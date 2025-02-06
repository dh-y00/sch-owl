package com.sch.owl.config.security.filter;

import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.token.TokenDeal;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 * 
 * @author ruoyi
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{

    private ILoginAuthenticationTokenVerity loginAuthenticationVerity;

    private TokenDeal tokenDeal;

    public JwtAuthenticationTokenFilter(ILoginAuthenticationTokenVerity loginAuthenticationVerity, TokenDeal tokenDeal) {
        this.loginAuthenticationVerity = loginAuthenticationVerity;
        this.tokenDeal = tokenDeal;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        loginAuthenticationVerity.verity(tokenDeal.getToken(request), request);
        chain.doFilter(request, response);
    }
}
