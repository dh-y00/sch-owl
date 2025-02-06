package com.sch.owl.config;

import cn.hutool.core.util.IdUtil;
import com.sch.owl.IRemoteUpmsServe;
import com.sch.owl.cache.OwlDeptSseEvent;
import com.sch.owl.cache.OwlUserSseEvent;
import com.sch.owl.config.listener.SseApplicationListener;
import com.sch.owl.properties.OwlBaseProperties;
import com.sch.owl.properties.OwlServerProperties;
import com.sch.owl.properties.OwlSseProperties;
import com.sch.owl.sse.SseClient;
import com.sch.owl.sse.task.SseClientTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(prefix = "sch.owl.sse", name = "enabled", havingValue = "true")
@AutoConfigureBefore(OwlRemoteConfiguration.class)
public class OwlSseConfiguration implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(OwlSseConfiguration.class);

    @Bean
    public SseClient sseClient(OwlServerProperties owlServerProperties, OwlBaseProperties owlBaseProperties, OwlSseProperties owlSseProperties) {
        String brokerId = applicationContext.getEnvironment().getProperty("spring.application.name") + "-" + IdUtil.getSnowflakeNextIdStr();
        SseClient sseClient = new SseClient(owlServerProperties, owlBaseProperties, owlSseProperties, brokerId);
        sseClient.linkSse();
        return sseClient;
    }

    @Bean
    public SseApplicationListener sseApplicationListener() {
        return new SseApplicationListener();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SseClientTask sseClientTask(SseClient sseClient) {
        return new SseClientTask(sseClient);
    }

    @Bean
    public OwlUserSseEvent upmsUserSseEvent(IRemoteUpmsServe remoteUpmsServe) {
        return new OwlUserSseEvent(remoteUpmsServe);
    }

    @Bean
    public OwlDeptSseEvent upmsDeptSseEvent(IRemoteUpmsServe remoteUpmsServe) {
        return new OwlDeptSseEvent(remoteUpmsServe);
    }
}
