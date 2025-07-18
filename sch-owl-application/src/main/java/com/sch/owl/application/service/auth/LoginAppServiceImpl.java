package com.sch.owl.application.service.auth;

import com.sch.owl.application.converter.auth.LoginAppConverter;
import com.sch.owl.application.reponse.auth.LoginUserResp;
import com.sch.owl.application.request.auth.LoginCommand;
import com.sch.owl.domain.service.auth.LoginDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAppServiceImpl implements LoginAppService {

    @Autowired
    private LoginDomain loginDomain;

    @Override
    public String login(LoginCommand loginCommand) {
        return loginDomain.login(LoginAppConverter.INSTANCE.converter(loginCommand));
    }

    @Override
    public LoginUserResp loginUser() {
        return null;
    }
}
