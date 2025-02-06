package com.sch.owl.mapper;

import com.sch.owl.entity.OwlUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表; Mapper 接口
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
public interface OwlUserMapper extends BaseMapper<OwlUser> {

    List<Map> queryAllToMap(@Param("page") int page, @Param("limit") int limit);
}
