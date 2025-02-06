package com.sch.owl.config.security;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.IRemoteUpmsServe;
import com.sch.owl.params.ClientGetUserParam;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RemoteJwtLoginAuthenticationTokenVerity implements ILoginAuthenticationTokenVerity {

    private IRemoteUpmsServe remoteUpmsServe;

    public RemoteJwtLoginAuthenticationTokenVerity(IRemoteUpmsServe remoteUpmsServe) {
        this.remoteUpmsServe = remoteUpmsServe;
    }

    @Override
    public void verity(String token, HttpServletRequest request) {
        ClientGetUserParam clientGetUserParam = new ClientGetUserParam();
        clientGetUserParam.setToken(token);
        clientGetUserParam.setNeedVerify(StringUtils.isNull(SecurityUtils.getAuthentication()));
        LoginUser loginUser = remoteUpmsServe.getUserInfoByToken(clientGetUserParam, LoginUser.class);
        if(Objects.nonNull(loginUser) && clientGetUserParam.isNeedVerify()) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
