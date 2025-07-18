package com.sch.owl.domain.repository.auth;

import java.util.concurrent.TimeUnit;

/**
 * 密码相关仓储
 */
public interface PasswordRepository {

    /**
     * 获取失败次数
     *
     * @param failKey 失败的key
     * @return 失败次数
     */
    Integer getRetryCount(String failKey);

    /**
     * 设置重试次数
     * @param failKey 失败的key
     * @param retryCount 重试次数
     * @param lockTime 锁定过期时间
     * @param timeUnit 时间单位
     */
    void setRetryCount(String failKey, Integer retryCount, Integer lockTime, TimeUnit timeUnit);

    /**
     * 判断是否有失败key
     *
     * @param failKey 失败的key
     * @return true-代表存在 false-代表不存在
     */
    boolean hasFailKey(String failKey);

    /**
     * 删除失败key
     *
     * @param failKey 失败的key
     */
    void deleteFailKey(String failKey);
}
