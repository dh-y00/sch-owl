package com.sch.owl.response;

import java.util.List;
import java.util.Map;

public class DeptExtraResponse {

    private DeptQwResponse deptQwResponse;

    private SaleDeptResponse saleDeptResponse;

    private Map<String, List<String>> deptTag;

    public SaleDeptResponse getSaleDeptResponse() {
        return saleDeptResponse;
    }

    public void setSaleDeptResponse(SaleDeptResponse saleDeptResponse) {
        this.saleDeptResponse = saleDeptResponse;
    }

    public Map<String, List<String>> getDeptTag() {
        return deptTag;
    }

    public void setDeptTag(Map<String, List<String>> deptTag) {
        this.deptTag = deptTag;
    }

    public DeptQwResponse getDeptQwResponse() {
        return deptQwResponse;
    }

    public void setDeptQwResponse(DeptQwResponse deptQwResponse) {
        this.deptQwResponse = deptQwResponse;
    }
}
