package com.sch.owl.permission.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 * 
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScopeNew
{
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";

    /**
     * 权限字符（用于多个角色匹配符合要求的权限）默认根据权限注解@ss获取，多个权限用逗号分隔开来
     */
    public String permission() default "";

    /**
     * 权限代码片段 code
     * 逻辑是 根据 code 寻找处理器 返回对应的 代码片段
     * @return 返回对应code
     */
    String dataScopeSqlCode() default "";
}
