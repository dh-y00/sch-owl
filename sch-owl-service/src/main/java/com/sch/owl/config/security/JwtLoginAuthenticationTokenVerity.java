package com.sch.owl.config.security;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.application.service.auth.TokenAppService;
import com.sch.owl.model.UserDetail;
import com.sch.owl.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtLoginAuthenticationTokenVerity implements ILoginAuthenticationTokenVerity {

    @Autowired
    private TokenAppService tokenAppService;

    @Override
    public void verity(String token, HttpServletRequest request) {
        UserDetail userDetail = tokenAppService.getLoginUserByToken(token);
        if (StringUtils.isNotNull(userDetail) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenAppService.verifyUserDetail(userDetail);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
