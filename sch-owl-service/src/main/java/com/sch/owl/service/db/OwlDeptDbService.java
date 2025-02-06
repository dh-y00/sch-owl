package com.sch.owl.service.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sch.owl.entity.OwlDept;
import com.sch.owl.mapper.OwlDeptMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sch.owl.pojo.dto.DeptListSearchDto;
import com.sch.owl.pojo.vo.DeptListVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表; 服务实现类
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Service
public class OwlDeptDbService extends ServiceImpl<OwlDeptMapper, OwlDept> {

    public List<Long> queryDeptIdAndChildDeptId(Long deptId) {
        return baseMapper.queryDeptIdAndChildDeptId(deptId);
    }

    public List<Long> queryDeptIdByRole(Long roleId) {
        return baseMapper.queryDeptIdByRole(roleId);
    }

    public List<DeptListVo> list(DeptListSearchDto deptListSearchDto){
        return baseMapper.list(deptListSearchDto);
    }

    public List<OwlDept> queryByParent(Long parentId) {
        LambdaQueryWrapper<OwlDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OwlDept::getParentId, parentId);
        return this.list(wrapper);
    }

    public OwlDept queryById(Long deptId) {
        LambdaQueryWrapper<OwlDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OwlDept::getId, deptId);
        return this.getOne(wrapper);
    }

}
