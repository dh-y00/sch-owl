package com.sch.owl.service.db;

import com.sch.owl.infrastructure.entity.auth.SysLoginHistory;
import com.sch.owl.infrastructure.mapper.auth.SysLoginHistoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录历史表; 服务实现类
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Service
public class SysLoginHistoryDbService extends ServiceImpl<SysLoginHistoryMapper, SysLoginHistory> {

}
