package com.sch.owl.mapper;

import com.sch.owl.entity.OwlApplicationEle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 应用元素; Mapper 接口
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
public interface OwlApplicationEleMapper extends BaseMapper<OwlApplicationEle> {

    List<OwlApplicationEle> queryByAppCodeAndType(@Param("appCode") String appCode,@Param("roleIds") List<Long> roleIds,@Param("typeCode") String[] typeCode);
}
