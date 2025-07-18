package com.sch.owl.config.security;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.IRemoteOwlServe;
import com.sch.owl.model.UserDetail;
import com.sch.owl.params.ClientGetUserParam;
import com.sch.owl.utils.SecurityUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RemoteJwtLoginAuthenticationTokenVerity implements ILoginAuthenticationTokenVerity {

    private IRemoteOwlServe remoteUpmsServe;

    public RemoteJwtLoginAuthenticationTokenVerity(IRemoteOwlServe remoteUpmsServe) {
        this.remoteUpmsServe = remoteUpmsServe;
    }

    @Override
    public void verity(String token, HttpServletRequest request) {
        ClientGetUserParam clientGetUserParam = new ClientGetUserParam();
        clientGetUserParam.setToken(token);
        clientGetUserParam.setNeedVerify(StringUtils.isNull(SecurityUtils.getAuthentication()));
        UserDetail userDetail = remoteUpmsServe.getUserInfoByToken(clientGetUserParam, UserDetail.class);
        if(Objects.nonNull(userDetail) && clientGetUserParam.isNeedVerify()) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
