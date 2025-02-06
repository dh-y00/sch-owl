package com.sch.owl.controller.client;

import com.sch.owl.config.web.GlobalDeal;
import com.sch.owl.pojo.dto.SseSendDto;
import com.sch.owl.service.sse.SseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/inner/sse")
public class SseController {

    @Autowired
    private SseServer sseServer;

    /**
     * 建立外呼通道
     * @return SseEmitter
     */
    @GetMapping("/connect/create")
    public SseEmitter createConnect(@RequestParam(name = "applicationName") String applicationName, @RequestParam(name = "brokerId") String brokerId) {
        if(StringUtils.isBlank(applicationName)) {
            throw new RuntimeException( "applicationName不能为空");
        }
        return sseServer.createSse(applicationName, brokerId);
    }

    @PostMapping("/sendMessage")
    @GlobalDeal
    public void sendMessage(@RequestBody SseSendDto sseSendDto) {
        sseServer.sendMessage(sseSendDto);
    }

}
