package com.sch.owl.service;

import com.rdrk.rsf.framework.utils.ApplicationContextUtils;
import com.sch.owl.constant.MqTabEnum;
import com.sch.owl.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class AbstractService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Logger mqLogger = LoggerFactory.getLogger("sys-mq");

    private static final String APP_TOPIC = "OWL_PLATFORM";

    public String getUsername()
    {
        return SecurityUtils.getLoginUser().getUsername();
    }

    /**
     * 同步推送一段消息到 mq
     * @param topSuffix 主题后缀
     * @param mqTabEnum 主题
     * @param message 消息
     */
    public void sendMqMessage(String topSuffix, MqTabEnum mqTabEnum, Object message) {
        String topic = APP_TOPIC;
        if(StringUtils.isNotBlank(topSuffix)) {
            topic = APP_TOPIC + "_" + topSuffix;
        }
        mqLogger.info("发送消息到 mq, topic: {}, tag: {}, message: {}", topic, mqTabEnum.getTab(), message);
        RocketMQTemplate rocketMQTemplate = ApplicationContextUtils.getBean(RocketMQTemplate.class);
        SendResult sendResult;
        try {
            if(Objects.isNull(mqTabEnum)) {
                sendResult = rocketMQTemplate.syncSend(topic, message, 1000);
            } else {
                sendResult = rocketMQTemplate.syncSend(topic + ":" + mqTabEnum.getTab(), message, 1000);
            }
        }catch (Exception e) {
            mqLogger.error(String.format("发送消息到 mq 异常, topic: %s, tag: %s, message: %s", topic, mqTabEnum.getTab(), message), e);
            throw e;
        }
        mqLogger.info("发送消息到 mq, topic: {}, tag: {}, message: {} sendResult: {}", topic, mqTabEnum.getTab(), message, sendResult);
    }

}
