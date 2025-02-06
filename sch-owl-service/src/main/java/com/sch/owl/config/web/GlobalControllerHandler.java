package com.sch.owl.config.web;

import com.alibaba.fastjson2.JSONObject;
import com.sch.owl.BaseResponse;
import com.sch.owl.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerHandler implements ResponseBodyAdvice<Object> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 这里就是通用的异常处理器了,所有预料之外的Exception异常都由这里处理
    @ExceptionHandler(BusinessException.class)
    public BaseResponse exceptionHandler(Exception e) {
        log.warn("业务异常: {}", e.getMessage());
        BusinessException be = (BusinessException) e;
        return BaseResponse.error(be.getCode(), be.getMessage());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getAnnotatedElement().isAnnotationPresent(GlobalDeal.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof BaseResponse) {
            return body;
        }
        if(body instanceof String || Objects.equals(returnType.getParameterType(), String.class)) {
//        if(body instanceof String) {
            // 由于 如果返回的对象是String 类型，spring mvc 再解析时会走 StringHttpMessageConverter ，导致出现 Response 无法转 String
            // 所以这一块，如果返回值是String的时候，我们则先将 string 放到 Response 里面去，然后 构建成, Json 字符串返回
            return JSONObject.toJSONString(BaseResponse.success(body));
        }
        return BaseResponse.success(body);
    }
}
