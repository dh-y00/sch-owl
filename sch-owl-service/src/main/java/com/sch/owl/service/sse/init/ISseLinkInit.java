package com.sch.owl.service.sse.init;

import cn.hutool.core.map.MapUtil;
import com.rdrk.rsf.framework.utils.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface ISseLinkInit {

    Logger LOG = LoggerFactory.getLogger("sse-init");

    void push(SseEmitter sseEmitter);

    static void init(SseEmitter sseEmitter) {
        Map<String, ISseLinkInit> beansOfType = ApplicationContextUtils.getBeansOfType(ISseLinkInit.class);
        if(MapUtil.isEmpty(beansOfType)) {
            return ;
        }
        for (ISseLinkInit sseLinkInit : beansOfType.values()) {
            try {
                sseLinkInit.push(sseEmitter);
            }catch (Exception e) {
                LOG.warn("sse 初始化推送失败：", e);
            }
        }
    }
}
