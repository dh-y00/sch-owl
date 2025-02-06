package com.sch.owl.config.mybatis;

import com.rdrk.rsf.db.config.IPrimaryMetaObjectHandler;
import com.rdrk.upms.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Objects;

@Configuration
public class MybatisAutoFillFieldValueConfig implements IPrimaryMetaObjectHandler {

    /**
     * 未登录的用户身份标识
     */
    private final static String ANONYMOUS_USER = "anonymousUser";
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);
//        anonymousUser
        if(Objects.isNull(SecurityUtils.getSecurityContext()) || Objects.isNull(SecurityUtils.getAuthentication())
                || Objects.equals(SecurityUtils.getAuthentication().getPrincipal(), ANONYMOUS_USER)) {
            this.strictInsertFill(metaObject, "createBy", String.class, "System");
            this.strictInsertFill(metaObject, "updateBy", String.class, "System");
        }else {
            this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getLoginUser().getUsername());
            this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getLoginUser().getUsername());
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if(Objects.isNull(SecurityUtils.getSecurityContext()) || Objects.isNull(SecurityUtils.getAuthentication())
                || Objects.equals(SecurityUtils.getAuthentication().getPrincipal(), ANONYMOUS_USER)) {
            this.strictInsertFill(metaObject, "updateBy", String.class, "System");
        }else {
            this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getLoginUser().getUsername());
        }
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }
}
