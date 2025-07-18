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
 * 应用元素;
 * </p>
 *
 * @author dh
 * @since 2025-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("owl_application_ele")
public class OwlApplicationEle extends BaseColumn {

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
     * 祖级
     */
    private String ancestors;

    /**
     * 父主键
     */
    private Long parentId;

    /**
     * 编码
     */
    private String code;

    /**
     * 元素名称
     */
    private String name;

    /**
     * 元素类型（M目录 C菜单 F按钮）
     */
    private String type;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 路由
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 顺序
     */
    private Integer order;

    /**
     * 乐观锁
     */
    private Integer version;

}
