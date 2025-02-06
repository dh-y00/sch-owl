package com.sch.owl.config;

import com.rdrk.upms.permission.PermissionService;
import com.rdrk.upms.permission.aspect.DataScopeAspect;
import com.rdrk.upms.permission.aspect.DataScopeNewAspect;
import org.springframework.context.annotation.Bean;

public class OwlPermissionConfiguration {

    @Bean(name = "ss")
    public PermissionService permissionService() {
        return new PermissionService();
    }

    @Bean
    public DataScopeAspect dataScopeAspect() {
        return new DataScopeAspect();
    }

    @Bean
    public DataScopeNewAspect dataScopeNewAspect() {
        return new DataScopeNewAspect();
    }

}
