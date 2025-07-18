package com.sch.owl;

import com.sch.owl.constant.ResponseConstants;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Data
public class BaseResponse<T> implements Serializable {


    private String code;
    private String msg;
    private T data;

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseResponse success() {
        return new BaseResponse(ResponseConstants.CODE_SUCCESS, ResponseConstants.SUCCESS_DEFAULT);
    }

    public static BaseResponse success(Object data) {
        return new BaseResponse(ResponseConstants.CODE_SUCCESS, ResponseConstants.SUCCESS_DEFAULT, data);
    }

    public static BaseResponse error(String message) {
        return new BaseResponse(ResponseConstants.CODE_ERROR_COMMON, message);
    }

    public static BaseResponse error(String code, String message) {
        return new BaseResponse(code, message);
    }

}


