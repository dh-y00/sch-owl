package com.sch.owl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_role")
public class OwlRole extends BaseColumn {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用主键
     */
    private Long applicationId;

    /**
     * 编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 默认数据范围
     */
    private Long dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 乐观锁
     */
    private Integer version;
}
