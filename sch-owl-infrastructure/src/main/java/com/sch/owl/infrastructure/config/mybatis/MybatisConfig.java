package com.sch.owl.infrastructure.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sch.owl.infrastructure.mapper")
public class MybatisConfig {
}
