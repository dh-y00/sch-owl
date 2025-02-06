package com.sch.owl.sse;

import cn.hutool.core.util.IdUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.rdrk.rsf.framework.exception.UtilException;
import com.rdrk.rsf.framework.utils.ApplicationContextUtils;
import com.sch.owl.config.listener.SseEvent;
import com.sch.owl.config.listener.SseEventInfo;
import com.sch.owl.constant.SseEventEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class CreateSseThread extends Thread {

    @Getter
    @Setter
    private String createLink;

    private LoadingCache<String, Boolean> EVENT_UUID_CACHE = CacheBuilder.newBuilder()
            //设置并发级别为 8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(1)
            // 初始化缓存容量
            .initialCapacity(10)
            // 最大缓存容量，超出就淘汰 —— 基于容量进行回收
            .maximumSize(2000L)
            // 设置缓存过期时间【写入缓存后多久过期】，超过这个时间就淘汰 —— 基于时间进行回收
            .expireAfterWrite(1L, TimeUnit.MINUTES)
            // key 使用弱引用 WeakReference
            .weakKeys()
            .build(new CacheLoader<String, Boolean>() {
                // 自动写缓存数据的方法
                @Override
                public Boolean load(String key) {
                    return false;
                }

                // 异步刷新缓存
                @Override
                public ListenableFuture<Boolean> reload(String key, Boolean oldValue) throws Exception {
                    return super.reload(key, oldValue);
                }
            });

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            URL url = new URL(createLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/event-stream");
            connection.setDoInput(true);
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while (!isInterrupted()) {
                String en = "";
                String data = "";
                String id = "";
                while ((line = reader.readLine()) != null) {
                    if(line.startsWith("id:")) {
                        log.debug("Event id: {}", id);
                        if(StringUtils.isNotBlank(id)) {
                            publish(en, data, id);
                        }
                        id = line.substring("id:".length()).trim();
                        // 这边认为 如果ID开始就是一个全新会话
                        en = "";
                        data = "";
                    } else if (line.startsWith("event:")) {
                        String eventName = line.substring("event:".length()).trim();
                        log.debug("Event Name: {}", eventName);
                        en = eventName;
                    } else if (line.startsWith("data:")) {
                        String eventData = line.substring("data:".length()).trim();
                        data = data + eventData;
                    } else if (line.isEmpty()) {
                        publish(en, data, id);
                    }
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            log.error("sse连接异常",e);
        }
    }

    private void publish(String en, String data, String id) {
        if(StringUtils.isBlank(en)) {
            return ;
        }
        log.debug("id: {}, evet: {}, 收到sse消息:{}",id, en ,data);
        if(StringUtils.equals(SseEventEnum.HEART_BEAT.getCode(),en)) {
            SseClient.LAST_HEARTBEAT_TIME = LocalDateTime.now();
            return ;
        }

        try {
            if(!lock.tryLock(30, TimeUnit.SECONDS)) {
                log.warn("以下消息未处理，原因：获取锁失败 id: {}, evet: {}, 收到sse消息:{}",id, en ,data);
            }
            if(EVENT_UUID_CACHE.get(id)) {
                return ;
            }
            EVENT_UUID_CACHE.put(id, true);
            String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
            SseEventInfo sseEventInfo = new SseEventInfo(en, data, snowflakeNextIdStr);
            ApplicationContextUtils.publish(new SseEvent(sseEventInfo));
        } catch (Exception e) {
            throw new UtilException(e);
        }finally {
            lock.unlock();
        }

    }

}
