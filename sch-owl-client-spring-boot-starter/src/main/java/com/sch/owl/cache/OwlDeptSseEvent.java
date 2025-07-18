package com.sch.owl.cache;

import com.alibaba.fastjson2.JSONObject;
import com.sch.owl.IRemoteOwlServe;
import com.sch.owl.constant.SseEventEnum;
import com.sch.owl.sse.ISseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class OwlDeptSseEvent implements ISseEvent {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private IRemoteOwlServe remoteUpmsServe;

    public OwlDeptSseEvent(IRemoteOwlServe remoteUpmsServe) {
        this.remoteUpmsServe = remoteUpmsServe;
    }

    @Override
    public void deal(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        DeptCacheInfo deptCacheInfo = new DeptCacheInfo();
        if(Objects.isNull(jsonObject.getLong("dept_id"))) {
            logger.warn("dept_id is null {}", data);
            return;
        }

        deptCacheInfo.setDeptId(jsonObject.getLong("dept_id"));
        deptCacheInfo.setParentId(jsonObject.getLong("parent_id"));
        deptCacheInfo.setAncestors(jsonObject.getString("ancestors"));
        deptCacheInfo.setDeptName(jsonObject.getString("dept_name"));
        deptCacheInfo.setStatus(jsonObject.getString("status"));
        deptCacheInfo.setAddress(jsonObject.getString("address"));
        DeptCache.setDeptInfo(deptCacheInfo);
    }

    @Override
    public String code() {
        return SseEventEnum.DEPT_INFO.getCode();
    }
}
