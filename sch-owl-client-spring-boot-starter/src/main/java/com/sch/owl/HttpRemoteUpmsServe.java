package com.sch.owl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rdrk.rsf.request.RequestService;
import com.rdrk.rsf.request.params.RequestParams;
import com.sch.owl.constant.ResponseConstants;
import com.sch.owl.exception.ServiceException;
import com.sch.owl.params.ClientGetUserParam;
import com.sch.owl.properties.OwlServerProperties;
import com.sch.owl.response.TokenConfigResponse;
import com.sch.owl.response.UserPostResponse;

import java.util.List;
import java.util.Objects;

public class HttpRemoteUpmsServe implements IRemoteUpmsServe {

    private OwlServerProperties umpsServerProperties;

    public HttpRemoteUpmsServe(OwlServerProperties umpsServerProperties) {
        this.umpsServerProperties = umpsServerProperties;
    }

    @Override
    public <T> T getUserInfoByToken(ClientGetUserParam clientGetUserParam, Class<T> clazz) {
        RequestParams params = new RequestParams();
        params.setParams(clientGetUserParam);
        String post = (String) RequestService.post(umpsServerProperties.getUrl() + umpsServerProperties.getUserByTokenUrl(), params);
        BaseResponse baseResponse = JSONObject.parseObject(post, BaseResponse.class);
        if(!Objects.equals(ResponseConstants.CODE_SUCCESS, baseResponse.getCode())) {
            throw new ServiceException("请求权限失败:" + baseResponse.getMsg());
        }
        if(Objects.isNull(baseResponse.getData())) {
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(baseResponse.getData()), clazz);
    }

    @Override
    public TokenConfigResponse getTokenConfig() {
        String post = (String) RequestService.post(umpsServerProperties.getUrl() + umpsServerProperties.getTokenConfigUrl(), new RequestParams());
        BaseResponse baseResponse = JSONObject.parseObject(post, BaseResponse.class);
        if(!Objects.equals(ResponseConstants.CODE_SUCCESS, baseResponse.getCode())) {
            throw new ServiceException("请求权限失败:" + baseResponse.getMsg());
        }
        if(Objects.isNull(baseResponse.getData())) {
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(baseResponse.getData()), TokenConfigResponse.class);
    }

    @Override
    public List<Long> getDeptIdAndChildDeptId(Long deptId) {
        String post = (String) RequestService.post(umpsServerProperties.getUrl() + umpsServerProperties.getDeptIdAndChildDeptIdUrl() + "?deptId="+deptId, new RequestParams());
        BaseResponse baseResponse = JSONObject.parseObject(post, BaseResponse.class);
        if(!Objects.equals(ResponseConstants.CODE_SUCCESS, baseResponse.getCode())) {
            throw new ServiceException("请求权限失败:" + baseResponse.getMsg());
        }
        if(Objects.isNull(baseResponse.getData())) {
            return null;
        }
        return JSONArray.parseArray(JSONObject.toJSONString(baseResponse.getData()), Long.class);
    }

    @Override
    public List<Long> getDeptIdByRole(Long roleId) {
        String post = (String) RequestService.post(umpsServerProperties.getUrl() + umpsServerProperties.getDeptIdByRoleUrl() + "?roleId="+roleId, new RequestParams());
        BaseResponse baseResponse = JSONObject.parseObject(post, BaseResponse.class);
        if(!Objects.equals(ResponseConstants.CODE_SUCCESS, baseResponse.getCode())) {
            throw new ServiceException("请求权限失败:" + baseResponse.getMsg());
        }
        if(Objects.isNull(baseResponse.getData())) {
            return null;
        }
        return JSONArray.parseArray(JSONObject.toJSONString(baseResponse.getData()), Long.class);
    }

    @Override
    public List<UserPostResponse> getPostCodeByUserId(Long userId) {
        String post = (String) RequestService.post(umpsServerProperties.getUrl() + umpsServerProperties.getPostCodeByUserIdUrl() + "?userId="+userId, new RequestParams());
        BaseResponse baseResponse = JSONObject.parseObject(post, BaseResponse.class);
        if(!Objects.equals(ResponseConstants.CODE_SUCCESS, baseResponse.getCode())) {
            throw new ServiceException("请求权限失败:" + baseResponse.getMsg());
        }
        if(Objects.isNull(baseResponse.getData())) {
            return null;
        }
        return JSONArray.parseArray(JSONObject.toJSONString(baseResponse.getData()), UserPostResponse.class);
    }
}
