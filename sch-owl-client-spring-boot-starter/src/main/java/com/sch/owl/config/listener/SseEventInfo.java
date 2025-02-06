package com.sch.owl.config.listener;

public class SseEventInfo {

    private String uuid;

    // 1-连接 2-关闭
    private String sseEvent;

    private String data;

    public SseEventInfo(String sseEvent, String data, String uuid) {
        this.uuid = uuid;
        this.sseEvent = sseEvent;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSseEvent() {
        return sseEvent;
    }

    public String getData() {
        return data;
    }
}
