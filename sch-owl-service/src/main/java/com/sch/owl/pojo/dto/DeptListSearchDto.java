package com.sch.owl.pojo.dto;

import com.sch.owl.model.BasePojo;
import lombok.Data;

@Data
public class DeptListSearchDto extends BasePojo {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态
     */
    private String status;
}
