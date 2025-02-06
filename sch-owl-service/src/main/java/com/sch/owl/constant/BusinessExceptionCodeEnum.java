package com.sch.owl.constant;

import lombok.Getter;

/**
 * 请求异常的 code 码
 * @author yaodonghu
 */
@Getter
public enum BusinessExceptionCodeEnum {

    ERR_owl1000("系统异常", "系统级的异常编码"),
    ERR_owl1003("%s", "请求 参数校验不通过"),
    ERR_owl2913("创建连接失败", "创建连接失败"),
    ERR_owl2915("断开连接", "发送消息失败"),
    ;

    private String code;

    private String message;

    private String desc;

    BusinessExceptionCodeEnum(String message, String desc) {
        String name = this.name();
        this.code = name.split(Constants.UNDERLINE)[1];
        this.message = message;
        this.desc = desc;
    }
}
