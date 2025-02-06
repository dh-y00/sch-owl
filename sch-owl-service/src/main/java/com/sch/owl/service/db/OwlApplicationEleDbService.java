package com.sch.owl.service.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sch.owl.entity.OwlApplicationEle;
import com.sch.owl.mapper.OwlApplicationEleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 应用元素; 服务实现类
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Service
public class OwlApplicationEleDbService extends ServiceImpl<OwlApplicationEleMapper, OwlApplicationEle> {

    public List<OwlApplicationEle> queryByAppCodeAndType(String appCode, List<Long> roleIds, String... typeCode) {
        return baseMapper.queryByAppCodeAndType(appCode, roleIds, typeCode);
    }

    public List<OwlApplicationEle> queryByAppCode(String appCode) {
        LambdaQueryWrapper<OwlApplicationEle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OwlApplicationEle::getApplicationCode, appCode);
        wrapper.orderBy(true, true,OwlApplicationEle::getOrder);
        return this.list(wrapper);
    }
}
