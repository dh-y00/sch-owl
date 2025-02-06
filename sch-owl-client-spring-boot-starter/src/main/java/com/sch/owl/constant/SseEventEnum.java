package com.sch.owl.constant;

import lombok.Getter;

@Getter
public enum SseEventEnum {

    HEART_BEAT("心跳"),
    USER_INFO("用户信息更新"),
    DICT_INFO("字典更新"),
    USER_INIT("用户信息初始化"),
    DEPT_ADD("部门新增"),
    DEPT_INFO("部门信息更新"),
    USER_DEL("用户删除 物理"),
    DEPT_DEL("部门删除"),
    ZT_CALL_SET_PASSWORD("证通座机密码设置"),
    JOB_INFO("定时任务信息"),
    JOB_CHECK("定时任务检查");


    private String code;

    private String desc;

    SseEventEnum(String desc) {
        this.code = this.name();
        this.desc = desc;
    }

}
