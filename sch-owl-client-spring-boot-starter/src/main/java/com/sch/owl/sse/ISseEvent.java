package com.sch.owl.sse;


import com.sch.owl.constant.SseEventEnum;

public interface ISseEvent {

    /**
     * 处理事件
     * @param data 事件数据
     */
    void deal(String data);

    /**
     * 事件码
     * @see SseEventEnum
     * @return 事件码
     */
    String code();

}
