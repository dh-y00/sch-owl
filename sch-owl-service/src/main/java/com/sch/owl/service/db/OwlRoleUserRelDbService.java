package com.sch.owl.service.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sch.owl.entity.OwlRoleUserRel;
import com.sch.owl.mapper.OwlRoleUserRelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sch.owl.model.LoginUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色用户关系表; 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-02-06
 */
@Service
public class OwlRoleUserRelDbService extends ServiceImpl<OwlRoleUserRelMapper, OwlRoleUserRel> {

    public List<Long> queryRoleIdByUserId(Long userId) {
        LambdaQueryWrapper<OwlRoleUserRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(OwlRoleUserRel::getRoleId);
        wrapper.eq(OwlRoleUserRel::getUserId, userId);
        List<OwlRoleUserRel> list = list(wrapper);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(OwlRoleUserRel::getRoleId).collect(Collectors.toList());
    }
}
