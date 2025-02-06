package com.sch.owl.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.rdrk.rsf.framework.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserCache {

    private static LoadingCache<Long, UserCacheInfo> USER_CACHE = CacheBuilder.newBuilder()
            //设置并发级别为 8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            // 初始化缓存容量
            .initialCapacity(10)
            // 最大缓存容量，超出就淘汰 —— 基于容量进行回收
            .maximumSize(2000L)
            // 是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            // 当 Entry 被移除时的监听器
            .removalListener(notification -> log.debug("user cache=" + notification))
            // 创建一个 CacheLoader，重写 load() 方法，以实现"当 get() 时缓存不存在，则调用 load() 方法，放到缓存并返回"的效果
            .build(new CacheLoader<Long, UserCacheInfo>() {

                // 自动写缓存数据的方法
                @Override
                public UserCacheInfo load(Long key) throws Exception {
                    return null;
                }

                // 异步刷新缓存
                @Override
                public ListenableFuture<UserCacheInfo> reload(Long key, UserCacheInfo oldValue) throws Exception {
                    return super.reload(key, oldValue);
                }
            });

    /**
     * 根据部门ID获取用户列表
     * @param deptId 部门的ID
     * @return  返回对应用户列表
     */
    public static List<UserCacheInfo> getUserByDeptId(Long deptId) {
        List<UserCacheInfo> allUserCache = getAllUserCache();
        if(CollectionUtils.isEmpty(allUserCache)) {
            return Collections.emptyList();
        }
        return allUserCache.stream().filter(userCacheInfo -> userCacheInfo.getDeptId().equals(deptId)).collect(Collectors.toList());
    }

    /**
     * 获取一下所有用户信息
     * @return 返回所有用户信息
     */
    public static List<UserCacheInfo> getAllUserCache() {
        return new ArrayList<>(USER_CACHE.asMap().values());
    }

    /**
     * 根据userId 获取一下用户信息
     * @param userId 用户的ID
     * @return 用户缓存
     */
    public static UserCacheInfo getUserById(Long userId) {
        try {
            return USER_CACHE.get(userId);
        }catch (CacheLoader.InvalidCacheLoadException invalidCacheLoadException) {
            return null;
        }catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 设置一下用户信息
     * @param userCacheInfo
     */
    public static void setUserInfo(UserCacheInfo userCacheInfo) {
        USER_CACHE.put(userCacheInfo.getUserId(), userCacheInfo);
    }
}
