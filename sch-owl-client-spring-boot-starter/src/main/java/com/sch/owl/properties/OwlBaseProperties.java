package com.sch.owl.properties;
import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.IRemoteUpmsServe;
import com.sch.owl.ITokenConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sch.owl.base")
public class OwlBaseProperties {

    /**
     * 登录认证token验证
     */
    private Class<? extends ILoginAuthenticationTokenVerity> loginAuthenticationTokenVerity;

    /**
     * token配置
     */
    private Class<? extends ITokenConfig> tokenConfig;

    /**
     * 远程服务
     */
    private Class<? extends IRemoteUpmsServe> remoteClazz;

    /**
     * 忽略那些路径
     */
    private String ignore;

    /**
     * 应用名称
     */
    private String applicationName;

}

