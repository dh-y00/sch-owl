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
 * 服务表;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_service")
public class OwlService extends BaseColumn {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用code
     */
    private String applicationCode;

    /**
     * 编码
     */
    private String code;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 乐观锁
     */
    private Integer version;

}
