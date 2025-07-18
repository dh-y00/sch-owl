package com.sch.owl.token;

import com.sch.owl.IRemoteOwlServe;
import com.sch.owl.ITokenConfig;
import com.sch.owl.response.TokenConfigResponse;
import org.apache.commons.lang3.StringUtils;


public class RemoteTokenConfig implements ITokenConfig {

    private String headerTokenName;

    private IRemoteOwlServe remoteUpmsServe;

    @Override
    public String getHeaderTokenName() {
        if(StringUtils.isBlank(headerTokenName)) {
            TokenConfigResponse tokenConfigResponse = remoteUpmsServe.getTokenConfig();
            headerTokenName = tokenConfigResponse.getHeaderTokenName();
        }
        return headerTokenName;
    }

    public void setRemoteUpmsServe(IRemoteOwlServe remoteUpmsServe) {
        this.remoteUpmsServe = remoteUpmsServe;
    }

    public void setHeaderTokenName(String headerTokenName) {
        this.headerTokenName = headerTokenName;
    }
}
