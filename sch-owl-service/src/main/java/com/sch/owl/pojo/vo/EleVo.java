package com.sch.owl.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class EleVo {

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     * */
    private String icon;

    /**
     * 是否为外链
     * */
    private Boolean isFrame;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 显示顺序
     * */
    private Integer order;

    /**
     * 显示状态（0显示 1隐藏）
     **/
    private Boolean visible;

    /**
     * 类型
     */
    private String type;

    /**
     * 子集
     */
    private List<EleVo> children;
}
