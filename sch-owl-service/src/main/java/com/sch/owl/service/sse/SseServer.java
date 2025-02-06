package com.sch.owl.service.sse;

import com.sch.owl.constant.BusinessExceptionCodeEnum;
import com.sch.owl.constant.MqTabEnum;
import com.sch.owl.constant.SseEventEnum;
import com.sch.owl.exception.BusinessException;
import com.sch.owl.pojo.dto.SseSendDto;
import com.sch.owl.service.AbstractService;
import com.sch.owl.service.sse.init.ISseLinkInit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class SseServer extends AbstractService {

    @Autowired
    private SseCache sseCache;

    /**
     * 创建连接
     */
    public SseEmitter createSse(String uid, String brokerId) {
        String key = SseCache.getKey(uid, brokerId);
        //默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0L);
        if(sseCache.hasKeyFromRedis(key)) {
            closeSseMq(key);
        }
        //完成后回调
        sseEmitter.onCompletion(() -> {
            log.info("[{}]结束连接...................", key);
            sseCache.remove(key);
        });
        //超时回调
        sseEmitter.onTimeout(() -> {
            log.info("[{}]连接超时...................", key);
            sseCache.remove(key);
        });
        //异常回调
        sseEmitter.onError(
            throwable -> {
                log.info("[{}]连接异常,{}", key, throwable.toString());
            }
        );
        try {
            sseEmitter.send(SseEmitter.event().reconnectTime(5000));
        } catch (IOException e) {
            throw new BusinessException(BusinessExceptionCodeEnum.ERR_owl2913);
        }
        sseCache.putSseEmitter(key, sseEmitter);
        // 创建连接成功后，需要将用户信息和部门信息等相关初始化信息都要推一边
        ISseLinkInit.init(sseEmitter);
        log.info("[{}]创建sse连接成功！", key);
        return sseEmitter;
    }

    /**
     * 通过mq发送消息
     * @param sseSendDto sseSendDto
     * @return true - 成功 false - 失败
     */
    public boolean sendMessageByMq(SseSendDto sseSendDto) {
        if (StringUtils.isBlank(sseSendDto.getData())) {
            return false;
        }
        Set<Object> keys = sseCache.allKeyFormRedis();
        if(StringUtils.isNotBlank(sseSendDto.getApplicationName())) {
            keys.removeIf(key -> !key.toString().startsWith(SseCache.getKeyPre(sseSendDto.getApplicationName())));
        }
        if(CollectionUtils.isEmpty(keys)) {
            log.warn("sse未找到用户{}的sse连接", sseSendDto.getApplicationName());
            return false;
        }
        for (Object key : keys) {
            try {
                sseSendDto.setKey((String) key);
                sendMqMessage("SSE_CLIENT", MqTabEnum.UPMS_SSE_SEND, sseSendDto);
            }catch (Exception e) {
                log.error("sse异步发送消息异常",e);
            }
        }
        return true;
    }

    /**
     * 给指定用户发送消息
     *
     */
    public boolean sendMessage(SseSendDto sseSendDto) {
        if (StringUtils.isBlank(sseSendDto.getData())) {
            return false;
        }
        if(!sseCache.isContains(sseSendDto.getKey())) {
            return false;
        }
        SseEmitter sseEmitter = sseCache.getSseEmitter(sseSendDto.getKey());
        String uuid = UUID.randomUUID().toString();
        if (sseEmitter == null) {
            log.info("消息推送失败uid:[{}], key {},没有创建连接，请重试。", uuid, sseSendDto.getKey());
            return false;
        }
        try {
            sseEmitter.send(SseEmitter.event().id(uuid).name(sseSendDto.getEventName()).reconnectTime(1*60*1000L).data(sseSendDto.getData()));
            log.info("key{},消息id:{},推送成功:{}", sseSendDto.getKey(), uuid, sseSendDto.getData());
            return true;
        }catch (Exception e) {
            log.info("用户{},消息id:{},推送异常:{}", sseSendDto.getKey(), uuid, e.getMessage());
//            sseEmitter.complete();
            sseEmitter.completeWithError(e);
            return false;
        }
    }

    /**
     * 断开
     * @param key
     */
    public void closeSseMq(String key){
        sendMqMessage("SSE_CLIENT", MqTabEnum.UPMS_SSE_CLOSE, key);
    }

    /**
     * 断开
     * @param key
     */
    public void closeSse(String key){
        if (sseCache.isContains(key)) {
//            SseEmitter sseEmitter = sseCache.getSseEmitter(key);
            sseCache.remove(key);
        }
    }

    public void checkSurvival() {
        log.debug("sse心跳检测开始...................");
        if(sseCache.isEmpty()) {
            log.info("当前sse列表为空");
            return ;
        }
        for (String key : sseCache.allKey()) {
            try {
                SseEmitter sseEmitter = sseCache.getSseEmitter(key);
                sseEmitter.send(SseEmitter.event().id(UUID.randomUUID().toString())
                        .name(SseEventEnum.HEART_BEAT.getCode())
                        .reconnectTime(1*60*1000L).data("还活着"));
                sseCache.flushSseKeyTime(key);
            }catch (Exception e) {
                log.error("sse心跳检测异常",e);
            }
        }
    }
}