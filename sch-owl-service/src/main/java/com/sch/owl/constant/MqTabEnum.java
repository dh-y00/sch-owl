package com.sch.owl.constant;

import lombok.Getter;

@Getter
public enum MqTabEnum {

    /**
     * 主题枚举
     */
    UPMS_SSE_SEND("SSE 发送消息"),
    UPMS_SSE_CLOSE("SSE 关闭"),;

    private String tab;

    private String desc;

    MqTabEnum(String desc) {
        this.tab = this.name();
        this.desc = desc;
    }
}
