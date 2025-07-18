package com.sch.owl.config;

import com.sch.owl.HttpRemoteOwlServe;
import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.IRemoteOwlServe;
import com.sch.owl.ITokenConfig;
import com.sch.owl.config.security.RemoteJwtLoginAuthenticationTokenVerity;
import com.sch.owl.properties.OwlBaseProperties;
import com.sch.owl.properties.OwlServerProperties;
import com.sch.owl.response.TokenConfigResponse;
import com.sch.owl.token.RemoteTokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

public class OwlRemoteConfiguration implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(OwlRemoteConfiguration.class);

    @Bean
    public IRemoteOwlServe remoteUpmsServe(OwlServerProperties umpsServerProperties, OwlBaseProperties umpsBaseProperties) {
        Class<? extends IRemoteOwlServe> remoteClazz = umpsBaseProperties.getRemoteClazz();
        if(Objects.isNull(remoteClazz) || Objects.equals(remoteClazz, HttpRemoteOwlServe.class)) {
            return new HttpRemoteOwlServe(umpsServerProperties);
        }
        return applicationContext.getBean(remoteClazz);
    }

    @Bean
    public ILoginAuthenticationTokenVerity loginAuthenticationTokenVerity(OwlBaseProperties umpsBaseProperties, ILoginAuthenticationTokenVerity remoteJwtLoginAuthenticationTokenVerity) {
        Class<? extends ILoginAuthenticationTokenVerity> loginAuthenticationTokenVerity = umpsBaseProperties.getLoginAuthenticationTokenVerity();
        if(Objects.isNull(loginAuthenticationTokenVerity)) {
            return remoteJwtLoginAuthenticationTokenVerity;
        }
        return applicationContext.getBean(loginAuthenticationTokenVerity);
    }

    @Bean
    public ITokenConfig tokenConfig(OwlBaseProperties umpsBaseProperties, IRemoteOwlServe remoteUpmsServe) {
        Class<? extends ITokenConfig> tokenConfig = umpsBaseProperties.getTokenConfig();
        if(Objects.isNull(tokenConfig) || Objects.equals(tokenConfig, RemoteTokenConfig.class)) {
            RemoteTokenConfig remoteTokenConfig = new RemoteTokenConfig();
            try {
                TokenConfigResponse tokenConfigResponse = remoteUpmsServe.getTokenConfig();
                remoteTokenConfig.setHeaderTokenName(tokenConfigResponse.getHeaderTokenName());

            }catch (Exception e) {
                logger.error("upms 获取远程Token配置失败", e);
            }
            remoteTokenConfig.setRemoteUpmsServe(remoteUpmsServe);
            return remoteTokenConfig;
        }
        return applicationContext.getBean(tokenConfig);
    }

    @Bean
    public ILoginAuthenticationTokenVerity remoteJwtLoginAuthenticationTokenVerity(IRemoteOwlServe remoteUpmsServe) {
        return new RemoteJwtLoginAuthenticationTokenVerity(remoteUpmsServe);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
