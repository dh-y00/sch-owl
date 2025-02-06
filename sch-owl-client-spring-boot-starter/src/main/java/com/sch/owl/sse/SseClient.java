package com.sch.owl.sse;


import com.rdrk.rsf.framework.utils.ApplicationContextUtils;
import com.sch.owl.properties.OwlBaseProperties;
import com.sch.owl.properties.OwlServerProperties;
import com.sch.owl.properties.OwlSseProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SseClient
 **/
@Slf4j
public class SseClient {

    private OwlServerProperties owlServerProperties;

    private OwlBaseProperties owlBaseProperties;

    private OwlSseProperties owlSseProperties;

    private String brokerId;

    private volatile CreateSseThread createSseThread;

    /**
     * 上一次心跳包的时间
     */
    public static volatile LocalDateTime LAST_HEARTBEAT_TIME;

    public SseClient(OwlServerProperties owlServerProperties, OwlBaseProperties owlBaseProperties, OwlSseProperties owlSseProperties, String brokerId) {
        this.owlServerProperties = owlServerProperties;
        this.owlBaseProperties = owlBaseProperties;
        this.owlSseProperties = owlSseProperties;
        this.brokerId = brokerId;
    }

    public void linkSse() {
        String requiredProperty = ApplicationContextUtils.getRequiredProperty("spring.application.name");
        if(StringUtils.isNotBlank(owlBaseProperties.getApplicationName())) {
            requiredProperty = owlBaseProperties.getApplicationName();
        }
        if(Objects.nonNull(createSseThread)) {
            createSseThread.interrupt();
        }
        createSseThread = new CreateSseThread();
        createSseThread.setCreateLink(owlServerProperties.getUrl() + owlSseProperties.getSseConnectUrl() +
                "?applicationName=" + requiredProperty + "&brokerId=" + brokerId);
        createSseThread.start();
    }

}
