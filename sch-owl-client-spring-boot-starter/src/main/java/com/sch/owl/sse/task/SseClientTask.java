package com.sch.owl.sse.task;

import com.sch.owl.sse.SseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
public class SseClientTask {

    private SseClient sseClient;

    public SseClientTask(SseClient sseClient) {
        this.sseClient = sseClient;
    }

    /**
     * 检查一下存活sse 发送一下心跳包
     */
    @Scheduled(cron = "0/20 * * * * ? ")
    public void checkSurvival() {
        LocalDateTime now = LocalDateTime.now();
        if(Objects.isNull(SseClient.LAST_HEARTBEAT_TIME)) {
            SseClient.LAST_HEARTBEAT_TIME = now;
            return;
        }
        if(now.minusSeconds(20).compareTo(SseClient.LAST_HEARTBEAT_TIME) > 0) {
            log.info("sse 尝试重连中");
            sseClient.linkSse();
        }
    }

}
