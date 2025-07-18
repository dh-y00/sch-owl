package com.sch.owl.infrastructure.entity.org;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.sch.owl.infrastructure.entity.BaseColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_dept")
public class OwlDept extends BaseColumn {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父类
     */
    private Long parentId;

    /**
     * 祖级
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态 0-无效 1-有效
     */
    private String status;

    /**
     * 乐观锁
     */
    private Integer version;
}
