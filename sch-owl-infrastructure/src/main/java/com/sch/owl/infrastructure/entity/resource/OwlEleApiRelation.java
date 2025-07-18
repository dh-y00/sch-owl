package com.sch.owl.infrastructure.entity.resource;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.sch.owl.infrastructure.entity.BaseColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 元素与接口关系;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_ele_api_relation")
public class OwlEleApiRelation extends BaseColumn {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 元素主键
     */
    private Long eleId;

    /**
     * 接口主键
     */
    private Long apiId;

    /**
     * 乐观锁
     */
    private Integer version;
}
