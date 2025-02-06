package com.sch.owl.exception;

import com.sch.owl.constant.BusinessExceptionCodeEnum;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class BusinessException extends RuntimeException{

    private String message;

    private String code;

    public BusinessException(BusinessExceptionCodeEnum businessExceptionCodeEnum, String... ex) {
        this(String.format(businessExceptionCodeEnum.getMessage(), Arrays.stream(ex).toArray()) , businessExceptionCodeEnum.getCode());
    }

    public BusinessException(Throwable cause, BusinessExceptionCodeEnum businessExceptionCodeEnum, String... ex) {
        this(String.format(businessExceptionCodeEnum.getMessage(), Arrays.stream(ex).toArray()) , businessExceptionCodeEnum.getCode(), cause);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BusinessException(String message, String code, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }
}
