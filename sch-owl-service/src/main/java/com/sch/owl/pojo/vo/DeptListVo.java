package com.sch.owl.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DeptListVo {

    private Long deptId;

    private Long parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 祖籍
     */
    private String ancestors;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private String status;
}
