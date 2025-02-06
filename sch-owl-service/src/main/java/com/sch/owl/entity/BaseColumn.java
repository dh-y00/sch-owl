package com.sch.owl.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * 
 * @author dh
 */
@Data
public class BaseColumn implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 仅仅只是用于统计
     */
    @TableField(value = "count(*)", insertStrategy = FieldStrategy.NEVER,updateStrategy = FieldStrategy.NEVER, select = false)
    private Long count;

    /**
     * 删除标识
     */
    @TableLogic
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private Integer delFlag;

    /** 创建者 */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新者 */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @TableField(value = "update_Time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
