package com.sch.owl.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 密码相关配置
 *
 * @author Ydh
 * @version 1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "sch.owl.password")
public class UserPasswordProperties {

    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;

    /**
     * 失败达到一定次数之后，锁定多长时间
     */
    private Integer lockTime;

}
