package com.sch.owl.config.listener;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.rdrk.rsf.framework.exception.UtilException;
import com.rdrk.rsf.framework.utils.ApplicationContextUtils;
import com.sch.owl.sse.ISseEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现ApplicationListener接口，里面的onApplicationEvent可能执行2次，分别是root application context和一般的容器
 * 这边只需要执行一次，所以 用锁配合缓存解决问题
 */
public class SseApplicationListener implements ApplicationListener<SseEvent> {

    private ExecutorService executorService = new ThreadPoolExecutor(5, 20,
            10L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(500), new ThreadPoolExecutor.CallerRunsPolicy());

    private Logger log = LoggerFactory.getLogger(SseApplicationListener.class);

    private Lock lock;

    private static LoadingCache<String, Boolean> EVENT_UUID_CACHE = CacheBuilder.newBuilder()
            //设置并发级别为 8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
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

    public SseApplicationListener() {
        log.info("初始化SseApplicationListener");
        lock = new ReentrantLock();
    }

    @Override
    public void onApplicationEvent(SseEvent event) {
        try {
            if(!lock.tryLock(10, TimeUnit.SECONDS)) {
                log.warn("获取锁失败, {}", event);
                return;
            }
            if(EVENT_UUID_CACHE.get(event.getUuid())) {
                return ;
            }
            EVENT_UUID_CACHE.put(event.getUuid(), true);
            log.debug("event-uuid: {}", event.getUuid());
        }catch (Exception e) {
            throw new UtilException(e);
        }finally {
            lock.unlock();
        }
        Map<String, ISseEvent> beansOfType = ApplicationContextUtils.getBeansOfType(ISseEvent.class);
        for (ISseEvent sseEvent : beansOfType.values()) {
            try {
                if(StringUtils.equals(sseEvent.code(), event.getSseEvent()) ) {
                    executorService.execute(() -> sseEvent.deal(event.getData()));
                }
            } catch (Exception e) {
                log.error("sse异步处理消息异常",e);
            }
        }
    }

}
