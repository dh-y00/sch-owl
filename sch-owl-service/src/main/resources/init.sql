DROP TABLE IF EXISTS owl_user;
CREATE TABLE owl_user(
     `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
     `nick_name` VARCHAR(50) NOT NULL  COMMENT '昵称' ,
     `user_name` VARCHAR(50) NOT NULL  COMMENT '用户名' ,
     `password` VARCHAR(255) NOT NULL  COMMENT '密码' ,
     `phone` VARCHAR(20)   COMMENT '手机号' ,
     `email` VARCHAR(100)   COMMENT '邮箱' ,
     `status` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '状态 0-无效 1- 有效' ,
     `version` INT NOT NULL  COMMENT '乐观锁' ,
     `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
     `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
     `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
     `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
     `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
     PRIMARY KEY (id)
)  COMMENT = '用户表;';

DROP TABLE IF EXISTS owl_dept;
CREATE TABLE owl_dept(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `parent_id` BIGINT NOT NULL  COMMENT '父类' ,
    `ancestors` VARCHAR(50) NOT NULL  COMMENT '祖级' ,
    `dept_name` VARCHAR(50) NOT NULL  COMMENT '部门名称' ,
    `status` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '状态 0-无效 1-有效' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL  COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '部门表;';

DROP TABLE IF EXISTS owl_user_dept_rel;
CREATE TABLE owl_user_dept_rel(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `user_id` BIGINT NOT NULL  COMMENT '用户主键' ,
    `dept_id` BIGINT NOT NULL  COMMENT '部门主键' ,
    `post_id` VARCHAR(255)   COMMENT '' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '用户部门关系表;';

DROP TABLE IF EXISTS owl_post;
CREATE TABLE owl_post(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父岗位' ,
    `ancestors` VARCHAR(50) NOT NULL  COMMENT '祖级' ,
    `post_code` VARCHAR(50) NOT NULL  COMMENT '岗位编码' ,
    `post_name` VARCHAR(50) NOT NULL  COMMENT '岗位名称' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '岗位表;';

DROP TABLE IF EXISTS owl_user_dept_post_relation;
CREATE TABLE owl_user_dept_post_relation(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `user_id` BIGINT NOT NULL  COMMENT '用户主键' ,
    `dept_id` BIGINT NOT NULL  COMMENT '部门主键' ,
    `post_id` BIGINT NOT NULL  COMMENT '岗位主键' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '用户部门岗位关系表;';

DROP TABLE IF EXISTS sys_login_history;
CREATE TABLE sys_login_history(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `user_name` VARCHAR(50) NOT NULL  COMMENT '用户名' ,
    `ip_addr` VARCHAR(50) NOT NULL  COMMENT '用户登录IP' ,
    `login_location` VARCHAR(50) NOT NULL  COMMENT '登录地点' ,
    `browser` VARCHAR(50) NOT NULL  COMMENT '浏览器' ,
    `os` VARCHAR(50) NOT NULL  COMMENT '系统' ,
    `status` VARCHAR(1) NOT NULL  COMMENT '登录状态（0成功 1失败）' ,
    `msg` VARCHAR(255) NOT NULL  COMMENT '登录信息' ,
    `login_time` DATETIME NOT NULL  COMMENT '登录时间' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '登录历史表;';


DROP TABLE IF EXISTS owl_appliction;
CREATE TABLE owl_application(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `code` VARCHAR(90) NOT NULL  COMMENT '编码' ,
    `name` VARCHAR(90) NOT NULL  COMMENT '应用名称' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '应用表;';

DROP TABLE IF EXISTS owl_application_ele;
CREATE TABLE owl_application_ele(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `application_code` VARCHAR(90) NOT NULL  COMMENT '应用主键' ,
    `ancestors` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '祖级' ,
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父主键' ,
    `code` VARCHAR(50) NOT NULL  COMMENT '编码' ,
    `name` VARCHAR(50) NOT NULL  COMMENT '元素名称' ,
    `type` VARCHAR(10) NOT NULL  COMMENT '元素类型（M目录 C菜单 F按钮）' ,
    `is_frame` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '是否为外链（0是 1否）' ,
    `visible` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '显示状态（0显示 1隐藏）' ,
    `status` VARCHAR(100) NOT NULL  COMMENT '状态（0正常 1停用）' ,
    `url` VARCHAR(255)   COMMENT '路由' ,
    `icon` VARCHAR(255)   COMMENT '图标' ,
    `order` INT NOT NULL DEFAULT 0 COMMENT '顺序' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '应用元素;';

DROP TABLE IF EXISTS owl_service;
CREATE TABLE owl_service(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `application_code` VARCHAR(90) NOT NULL  COMMENT '应用主键' ,
    `code` VARCHAR(90) NOT NULL  COMMENT '编码' ,
    `name` VARCHAR(50) NOT NULL  COMMENT '服务名称' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '服务表;';

DROP TABLE IF EXISTS owl_service_api;
CREATE TABLE owl_service_api(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `service_code` VARCHAR(90) NOT NULL  COMMENT '服务主键' ,
    `ele_ids` VARCHAR(50)   COMMENT '关联元素主键集合' ,
    `code` VARCHAR(50) NOT NULL  COMMENT '编码' ,
    `url_rep` VARCHAR(255)   COMMENT '接口URL' ,
    `name` VARCHAR(50) NOT NULL  COMMENT '接口名称' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '服务接口;';

DROP TABLE IF EXISTS owl_ele_api_relation;
CREATE TABLE owl_ele_api_relation(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `ele_id` BIGINT NOT NULL  COMMENT '元素主键' ,
    `api_id` BIGINT NOT NULL  COMMENT '接口主键' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '元素与接口关系;';


DROP TABLE IF EXISTS owl_role;
CREATE TABLE owl_role(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `application_id` BIGINT NOT NULL  COMMENT '应用主键' ,
    `code` VARCHAR(90) NOT NULL  COMMENT '编码' ,
    `name` VARCHAR(90) NOT NULL  COMMENT '角色名称' ,
    `data_scope` BIGINT   COMMENT '默认数据范围' ,
    `status` VARCHAR(10) NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '角色表;';

CREATE TABLE owl_role_user_rel(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `user_id` BIGINT NOT NULL  COMMENT '用户主键' ,
    `role_id` BIGINT NOT NULL  COMMENT '角色主键' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '角色用户关系表;';

DROP TABLE IF EXISTS owl_role_ele_rel;
CREATE TABLE owl_role_ele_rel(
    `id` BIGINT AUTO_INCREMENT COMMENT '主键' ,
    `role_id` BIGINT NOT NULL  COMMENT '角色主键' ,
    `ele_id` BIGINT NOT NULL  COMMENT '元素主键' ,
    `data_scope` BIGINT NOT NULL  COMMENT '数据范围' ,
    `version` INT NOT NULL  COMMENT '乐观锁' ,
    `del_flag` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除标识 0-没有删除 1-已经删除' ,
    `created_by` VARCHAR(50) NOT NULL  COMMENT '创建人' ,
    `created_time` DATETIME NOT NULL  COMMENT '创建时间' ,
    `updated_by` VARCHAR(50) NOT NULL  COMMENT '更新人' ,
    `updated_time` DATETIME NOT NULL  COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '角色和元素的关系;';
