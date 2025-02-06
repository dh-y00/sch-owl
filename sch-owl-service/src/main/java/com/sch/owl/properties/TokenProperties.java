package com.sch.owl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "sch.owl.token")
public class TokenProperties {

    /**
     * 令牌自定义标识
     */
    private String header;

    /**
     * secret
     */
    private String secret;

    /**
     * expireTime
     */
    private int expireTime;

}

