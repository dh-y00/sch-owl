package com.sch.owl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sch.owl.sse")
public class OwlSseProperties {


    /**
     * sse连接地址
     */
    private String sseConnectUrl = "/inner/sse/connect/create";

    /**
     * 是否开启 sse
     */
    private Boolean enabled;
}

