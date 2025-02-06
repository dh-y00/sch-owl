package com.sch.owl.cache;

import com.alibaba.fastjson2.JSONObject;
import com.sch.owl.IRemoteUpmsServe;
import com.sch.owl.constant.SseEventEnum;
import com.sch.owl.response.UserPostResponse;
import com.sch.owl.sse.ISseEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OwlUserSseEvent implements ISseEvent {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private IRemoteUpmsServe remoteUpmsServe;

    public OwlUserSseEvent(IRemoteUpmsServe remoteUpmsServe) {
        this.remoteUpmsServe = remoteUpmsServe;
    }

    @Override
    public void deal(String data) {

        JSONObject jsonObject = JSONObject.parseObject(data);
        UserCacheInfo userCacheInfo = new UserCacheInfo();
        if(Objects.isNull(jsonObject.getLong("user_id"))) {
            logger.warn("user_id is null {}", data);
            return;
        }
        userCacheInfo.setUserId(jsonObject.getLong("user_id"));
        userCacheInfo.setDeptId(jsonObject.getLong("dept_id"));
        userCacheInfo.setUserName(jsonObject.getString("user_name"));
        userCacheInfo.setNickName(jsonObject.getString("nick_name"));
        userCacheInfo.setPhonenumber(jsonObject.getString("nick_name"));
        userCacheInfo.setWorkPhone(jsonObject.getString("phonenumber"));
        userCacheInfo.setCallId(jsonObject.getLong("call_id"));
        userCacheInfo.setStatus(jsonObject.getString("status"));
        userCacheInfo.setWorkStatus(jsonObject.getInteger("work_status"));
        userCacheInfo.setQwStatus(jsonObject.getInteger("qw_status"));
        userCacheInfo.setQwUserId(jsonObject.getString("qw_user_id"));
        UserCacheInfo userById = UserCache.getUserById(userCacheInfo.getUserId());
        if(Objects.isNull(userById)) {
            List<UserPostResponse> postCodeByUserId = remoteUpmsServe.getPostCodeByUserId(userCacheInfo.getUserId());
            if(CollectionUtils.isEmpty(postCodeByUserId)) {
                userCacheInfo.setPosts(Collections.emptyList());
            } else {
                List<UserCacheInfo.UserPost> collect = postCodeByUserId.stream().map(userPostResponse -> {
                    UserCacheInfo.UserPost userPost = new UserCacheInfo.UserPost();
                    userPost.setCode(userPostResponse.getCode());
                    userPost.setName(userPostResponse.getName());
                    return userPost;
                }).collect(Collectors.toList());
                userCacheInfo.setPosts(collect);
            }
        } else {
            userCacheInfo.setPosts(userById.getPosts());
        }
        UserCache.setUserInfo(userCacheInfo);

    }

    @Override
    public String code() {
        return SseEventEnum.USER_INFO.getCode();
    }
}
