package com.sch.owl.config.listener;

import org.springframework.context.ApplicationEvent;

public class SseEvent extends ApplicationEvent {

//    private String uuid;
//
//    // 1-连接 2-关闭
//    private String sseEvent;
//
//    private String data;

    private SseEventInfo sseEventInfo;

    public SseEvent(SseEventInfo sseEventInfo) {
        super(sseEventInfo);
//        this.uuid = uuid;
//        this.sseEvent = sseEvent;
//        this.data = data;
        this.sseEventInfo = sseEventInfo;
    }

    public String getUuid() {
        return sseEventInfo.getUuid();
    }

    public String getSseEvent() {
        return sseEventInfo.getSseEvent();
    }

    public String getData() {
        return sseEventInfo.getData();
    }
}
