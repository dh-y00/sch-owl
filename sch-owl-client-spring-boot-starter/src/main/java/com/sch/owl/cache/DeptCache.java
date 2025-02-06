package com.sch.owl.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.rdrk.rsf.framework.exception.UtilException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DeptCache {

    private static LoadingCache<Long, DeptCacheInfo> DEPT_CACHE = CacheBuilder.newBuilder()
            //设置并发级别为 8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            // 初始化缓存容量
            .initialCapacity(10)
            // 最大缓存容量，超出就淘汰 —— 基于容量进行回收
            .maximumSize(2000L)
            // 是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            // 当 Entry 被移除时的监听器
            .removalListener(notification -> log.debug("dept cache=" + notification))
            // 创建一个 CacheLoader，重写 load() 方法，以实现"当 get() 时缓存不存在，则调用 load() 方法，放到缓存并返回"的效果
            .build(new CacheLoader<Long, DeptCacheInfo>() {

                // 自动写缓存数据的方法
                @Override
                public DeptCacheInfo load(Long key) throws Exception {
                    return null;
                }

                // 异步刷新缓存
                @Override
                public ListenableFuture<DeptCacheInfo> reload(Long key, DeptCacheInfo oldValue) throws Exception {
                    return super.reload(key, oldValue);
                }
            });

    private static LoadingCache<Long, List<Long>> CHILD_DEPT_CACHE = CacheBuilder.newBuilder()
            //设置并发级别为 8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            // 初始化缓存容量
            .initialCapacity(10)
            // 最大缓存容量，超出就淘汰 —— 基于容量进行回收
            .maximumSize(2000L)
            // 是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            // 设置缓存过期时间【写入缓存后多久过期】，超过这个时间就淘汰 —— 基于时间进行回收
            .expireAfterWrite(10L, TimeUnit.MINUTES)
            // 设置缓存刷新时间【写入缓存后多久刷新缓存】，超过这个时间就刷新缓存，并调用refresh方法，默认是异步刷新
            .refreshAfterWrite(5L, TimeUnit.MINUTES)
            // key 使用弱引用 WeakReference
            .weakKeys()
            // 当 Entry 被移除时的监听器
            .removalListener(notification -> log.debug("dept cache=" + notification))
            // 创建一个 CacheLoader，重写 load() 方法，以实现"当 get() 时缓存不存在，则调用 load() 方法，放到缓存并返回"的效果
            .build(new CacheLoader<Long, List<Long>>() {

                // 自动写缓存数据的方法
                @Override
                public List<Long> load(Long key) throws Exception {
                    if(Objects.equals(key, 0L)) {
                        return Arrays.asList(0L);
                    }
                    List<DeptCacheInfo> allDeptCache = getAllDeptCache();
                    List<Long> list = new ArrayList<>();
                    for (DeptCacheInfo deptCacheInfo : allDeptCache) {
                        String[] split = deptCacheInfo.getAncestors().split(",");
                        if(Arrays.asList(split).contains(key.toString())) {
                            list.add(deptCacheInfo.getDeptId());
                        }
                    }
                    list.add(key);
                    return list;
                }

                // 异步刷新缓存
                @Override
                public ListenableFuture<List<Long>> reload(Long key, List<Long> oldValue) throws Exception {
                    List<Long> list = new ArrayList<>();
                    if(!Objects.equals(key, 0L)) {List<DeptCacheInfo> allDeptCache = getAllDeptCache();
                        for (DeptCacheInfo deptCacheInfo : allDeptCache) {
                            if(deptCacheInfo.getAncestors().contains(key.toString())) {
                                list.add(deptCacheInfo.getDeptId());
                            }
                        }
                    }
                    list.add(key);
                    return super.reload(key, list);
                }
            });

    public static List<Long> getDeptIdAndChildDeptId(Long deptId) {
        try {
            return CHILD_DEPT_CACHE.get(deptId);
        }catch (CacheLoader.InvalidCacheLoadException invalidCacheLoadException) {
            return null;
        }catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 获取一下所有部门信息
     * @return 返回所有用户信息
     */
    public static List<DeptCacheInfo> getAllDeptCache() {
        return new ArrayList<>(DEPT_CACHE.asMap().values());
    }

    /**
     * 根据部门主键获取部门信息
     * @param deptId 部门的ID
     * @return 部门缓存
     */
    public static DeptCacheInfo getDeptId(Long deptId) {
        try {
            return DEPT_CACHE.get(deptId);
        }catch (CacheLoader.InvalidCacheLoadException invalidCacheLoadException) {
            return null;
        }catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 获得当前部门的二级部门信息
     * @param deptId 部门主键
     * @return 二级部门信息
     */
    public static DeptCacheInfo getSecondLevelDeptById(Long deptId) {
        DeptCacheInfo deptCacheInfo = getDeptId(deptId);
        if(Objects.isNull(deptCacheInfo)) {
            throw new UtilException("部门信息不存在");
        }
        String ancestors = deptCacheInfo.getAncestors();
        String[] split = ancestors.split(",");
        if(split.length == 2) {
            return deptCacheInfo;
        }else if(split.length < 3) {
            throw new UtilException("当前部门非二级部门以下部门");
        }
        return getDeptId(Long.parseLong(split[2]));
    }

    /**
     * 设置一下部门信息
     * @param deptCacheInfo 部门信息
     */
    public static void setDeptInfo(DeptCacheInfo deptCacheInfo) {
        DEPT_CACHE.put(deptCacheInfo.getDeptId(), deptCacheInfo);
    }
}
