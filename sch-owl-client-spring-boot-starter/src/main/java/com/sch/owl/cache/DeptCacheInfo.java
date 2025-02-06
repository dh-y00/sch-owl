package com.sch.owl.cache;

import lombok.Data;

@Data
public class DeptCacheInfo {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门状态
     */
    private String status;

    /**
     * 部门地址
     */
    private String address;
}
