package com.sch.owl.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.rdrk.rsf.framework.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PostCache {

    private static LoadingCache<String, List<UserCacheInfo>> POST_CACHE = CacheBuilder.newBuilder()
            //设置并发级别为 8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            // 初始化缓存容量
            .initialCapacity(10)
            // 最大缓存容量，超出就淘汰 —— 基于容量进行回收
            .maximumSize(500L)
            // 是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            // 设置缓存过期时间【写入缓存后多久过期】，超过这个时间就淘汰 —— 基于时间进行回收
            .expireAfterWrite(60L, TimeUnit.MINUTES)
            // 设置缓存刷新时间【写入缓存后多久刷新缓存】，超过这个时间就刷新缓存，并调用refresh方法，默认是异步刷新
            .refreshAfterWrite(20L, TimeUnit.MINUTES)
            // key 使用弱引用 WeakReference
            .weakKeys()
            // 当 Entry 被移除时的监听器
            .removalListener(notification -> log.debug("post cache=" + notification))
            // 创建一个 CacheLoader，重写 load() 方法，以实现"当 get() 时缓存不存在，则调用 load() 方法，放到缓存并返回"的效果
            .build(new CacheLoader<String, List<UserCacheInfo>>() {

                // 自动写缓存数据的方法
                @Override
                public List<UserCacheInfo> load(String key) throws Exception {
                    List<UserCacheInfo> allUserCache = UserCache.getAllUserCache();
                    List<UserCacheInfo> userCacheInfos = new ArrayList<>();
                    for (UserCacheInfo userCacheInfo : allUserCache) {
                        if(CollectionUtils.isNotEmpty(userCacheInfo.getPostCodes()) && userCacheInfo.getPostCodes().contains(key)) {
                            userCacheInfos.add(userCacheInfo);
                        }
                    }
                    return userCacheInfos;
                }

                // 异步刷新缓存
                @Override
                public ListenableFuture<List<UserCacheInfo>> reload(String key, List<UserCacheInfo> oldValue) throws Exception {
                    List<UserCacheInfo> allUserCache = UserCache.getAllUserCache();
                    List<UserCacheInfo> userCacheInfos = new ArrayList<>();
                    for (UserCacheInfo userCacheInfo : allUserCache) {
                        if(CollectionUtils.isNotEmpty(userCacheInfo.getPostCodes()) && userCacheInfo.getPostCodes().contains(key)) {
                            userCacheInfos.add(userCacheInfo);
                        }
                    }
                    return super.reload(key, userCacheInfos);
                }
            });

    /**
     * 根据userId 获取一下用户信息
     * @param postCode 岗位 code
     * @return 用户缓存
     */
    public static List<UserCacheInfo> getUserByPostCode(String postCode) {
        try {
            return POST_CACHE.get(postCode);
        }catch (Exception e) {
            throw new UtilException(e);
        }
    }
}
