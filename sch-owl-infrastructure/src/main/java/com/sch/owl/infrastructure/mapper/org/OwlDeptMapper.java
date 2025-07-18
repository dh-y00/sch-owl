package com.sch.owl.infrastructure.mapper.org;

import com.sch.owl.infrastructure.entity.org.OwlDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表; Mapper 接口
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
public interface OwlDeptMapper extends BaseMapper<OwlDept> {

    List<Long> queryDeptIdAndChildDeptId(Long deptId);

    List<Long> queryDeptIdByRole(Long roleId);

//    List<DeptListVo> list(DeptListSearchDto deptListSearchDto);

    List<Map> queryAllToMap(@Param("page") int page, @Param("limit") int limit);
}
