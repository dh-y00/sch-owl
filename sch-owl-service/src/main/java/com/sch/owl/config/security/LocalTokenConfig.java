package com.sch.owl.config.security;

import com.sch.owl.ITokenConfig;
import com.sch.owl.application.service.auth.TokenAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalTokenConfig implements ITokenConfig {

    @Autowired
    private TokenAppService tokenAppService;

    @Override
    public String getHeaderTokenName() {
        return tokenAppService.getHeaderTokenName();
    }
}
