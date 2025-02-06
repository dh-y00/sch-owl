package com.sch.owl.config.security;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.rdrk.upms.api.ILoginAuthenticationTokenVerity;
import com.rdrk.upms.model.LoginUser;
import com.rdrk.upms.service.TokenService;
import com.rdrk.upms.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtLoginAuthenticationTokenVerity implements ILoginAuthenticationTokenVerity {

    @Autowired
    private TokenService tokenService;

    @Override
    public void verity(String token, HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
