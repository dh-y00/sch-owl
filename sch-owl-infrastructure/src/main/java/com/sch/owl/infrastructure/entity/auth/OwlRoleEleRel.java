package com.sch.owl.infrastructure.entity.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.sch.owl.infrastructure.entity.BaseColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色和元素的关系;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_role_ele_rel")
public class OwlRoleEleRel extends BaseColumn {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色主键
     */
    private Long roleId;

    /**
     * 元素主键
     */
    private Long eleId;

    /**
     * 数据范围
     */
    private Long dataScope;

    /**
     * 乐观锁
     */
    private Integer version;

}
