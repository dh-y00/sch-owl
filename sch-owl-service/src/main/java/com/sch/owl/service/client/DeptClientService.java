package com.sch.owl.service.client;

import com.sch.owl.service.db.OwlDeptDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptClientService {

    @Autowired
    private OwlDeptDbService deptDbService;

    public List<Long> getDeptIdAndChildDeptId(Long deptId) {
        return deptDbService.queryDeptIdAndChildDeptId(deptId);
    }

    public List<Long> getDeptIdByRole(Long roleId) {
        return deptDbService.queryDeptIdByRole(roleId);
    }
}
