package com.sch.owl.config.security;

import com.rdrk.upms.api.ITokenConfig;
import com.rdrk.upms.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalTokenConfig implements ITokenConfig {

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public String getHeaderTokenName() {
        return tokenProperties.getHeader();
    }
}
