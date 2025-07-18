package com.sch.owl.domain.model.auth;

import com.sch.owl.model.UserDetail;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class TokenEntity {

    private String tokenKey;

    private UserDetail userDetail;

    private Integer expireTime;

    private TimeUnit timeUnit;

}
