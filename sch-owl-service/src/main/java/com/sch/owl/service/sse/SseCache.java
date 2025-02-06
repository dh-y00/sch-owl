package com.sch.owl.service.sse;

import cn.hutool.core.map.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseCache {

    @Autowired
    @Qualifier("strRedisTemplate")
    private RedisTemplate<String, String> sseRedisTemplate;

    private final static String UPMS_SSE_CLIENT = "UPMS_SSE_CLIENT";

    private static final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    public static String getKey(String uid, String brokerId) {
        return getKeyPre(uid) + ";brokerId:" + brokerId;
    }

    public static String getKeyPre(String uid) {
        return "applicationName:" + uid;
    }

    /**
     * 根据 applicationName 获取 sseEmitter
     * @param applicationName 应用名称
     * @return sseEmitter
     */
    public List<SseEmitter> getSseEmitterByApplicationName(String applicationName) {
        List<SseEmitter> sseEmitters = new ArrayList<>();
        for (String key : sseEmitterMap.keySet()) {
            if(key.startsWith(getKeyPre(applicationName))) {
                sseEmitters.add(sseEmitterMap.get(key));
            }
        }
        return sseEmitters;
    }

    public void putSseEmitter(String key, SseEmitter sseEmitter) {
        sseEmitterMap.put(key, sseEmitter);
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        sseRedisTemplate.opsForHash().put(UPMS_SSE_CLIENT, key, String.valueOf(zonedDateTime.toEpochSecond()));
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    }

    public void remove(String key) {
        sseRedisTemplate.opsForHash().delete(UPMS_SSE_CLIENT, key);
        sseEmitterMap.remove(key);
    }

    public void removeFormReds(String key) {
        sseRedisTemplate.opsForHash().delete(UPMS_SSE_CLIENT, key);
    }

    /**
     * 通过key 获取 sseEmitter
     * @param key sseEmitter 对应的 key
     * @return sseEmitter
     */
    public SseEmitter getSseEmitter(String key) {
        return sseEmitterMap.get(key);
    }

    /**
     * 当前节点sse map 是否为空
     * @return true-空 false-不为空
     */
    public Boolean isEmpty() {
        return MapUtil.isEmpty(sseEmitterMap);
    }

    /**
     * 是否包含 key
     * @param key sse 对应的 key
     * @return false-不包含 true-包含
     */
    public Boolean isContains(String key) {
        if(isEmpty()) {
            return false;
        }
        return sseEmitterMap.keySet().contains(key);
    }

    /**
     * 获取到本地所有的key
     * @return key集合
     */
    public Set<String> allKey() {
        return sseEmitterMap.keySet();
    }

    /**
     * redis 上面所有的ssekey有哪些
     * @return 所有的 sse key
     */
    public Set<Object> allKeyFormRedis() {
        return sseRedisTemplate.opsForHash().keys(UPMS_SSE_CLIENT);
    }


    /**
     *
     */
    public Object getValueFormRedis(String key) {
        return sseRedisTemplate.opsForHash().get(UPMS_SSE_CLIENT, key);
    }

    /**
     * 判断redis里面是否存在该key
     * @param key sse key
     * @return true-存在 false-不存在
     */
    public boolean hasKeyFromRedis(String key) {
        return sseRedisTemplate.opsForHash().hasKey(UPMS_SSE_CLIENT, key);
    }

    public void flushSseKeyTime(String key) {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        sseRedisTemplate.opsForHash().put(UPMS_SSE_CLIENT, key, String.valueOf(zonedDateTime.toEpochSecond()));

    }
}
