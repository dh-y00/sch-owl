package com.sch.owl.service.sse.init;

import com.sch.owl.constant.SseEventEnum;
import com.sch.owl.mapper.OwlDeptMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SseLinkInitDept implements ISseLinkInit{

    @Autowired
    private OwlDeptMapper deptMapper;


    @Override
    public void push(SseEmitter sseEmitter) {
        int page = 0;
        int limit = 100;
        do {
            List<Map> maps = deptMapper.queryAllToMap(page * limit, limit);
            if(CollectionUtils.isEmpty(maps)) {
                return ;
            }
            for (Map map : maps){
                String uuid = UUID.randomUUID().toString();
                try {
                    sseEmitter.send(SseEmitter.event().id(uuid).name(SseEventEnum.DEPT_INFO.getCode()).reconnectTime(1*60*1000L).data(map));
                }catch (Exception e ) {
                    ISseLinkInit.LOG.warn("部门初始化失败，", e);
                }
            }
            page ++;
        }while (true);

    }
}
