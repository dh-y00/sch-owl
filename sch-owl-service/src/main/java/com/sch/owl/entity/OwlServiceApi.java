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
 * 服务接口;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_service_api")
public class OwlServiceApi extends BaseColumn {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 服务code
     */
    private String serviceCode;

    /**
     * 关联元素主键集合
     */
    private String eleIds;

    /**
     * 编码
     */
    private String code;

    /**
     * 接口URL
     */
    private String urlRep;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 乐观锁
     */
    private Integer version;

}
