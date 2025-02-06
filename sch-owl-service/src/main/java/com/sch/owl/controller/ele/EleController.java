package com.sch.owl.controller.ele;

import com.sch.owl.pojo.vo.EleVo;
import com.sch.owl.service.AppEleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 元素相关 controller
 */
@RestController
@RequestMapping("/ele")
public class EleController {

    @Autowired
    private AppEleService appEleService;

    /**
     * 获取到指定某个系统下所有元素 以树的形式
     */
    public List<EleVo> getAppAllEleTree(@RequestHeader("App-Code") String appCode) {
        return appEleService.getAppAllEleTree(appCode);
    }
}
