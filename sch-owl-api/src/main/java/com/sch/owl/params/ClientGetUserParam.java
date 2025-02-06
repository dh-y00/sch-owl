package com.sch.owl.params;

public class ClientGetUserParam {

    private String token;

    private boolean needVerify = true;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isNeedVerify() {
        return needVerify;
    }

    public void setNeedVerify(boolean needVerify) {
        this.needVerify = needVerify;
    }
}
