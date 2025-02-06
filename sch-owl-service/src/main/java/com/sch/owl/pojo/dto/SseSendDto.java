package com.sch.owl.pojo.dto;

import com.sch.owl.constant.SseEventEnum;
import lombok.Data;

@Data
public class SseSendDto {

    /**
     * {@link SseEventEnum}
     */
    private String eventName;

    private String applicationName;

    private String key;

    private String data;

}
