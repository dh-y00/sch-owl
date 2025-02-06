package com.sch.owl.controller;

import com.sch.owl.pojo.vo.EleVo;
import com.sch.owl.service.AppEleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/menu")
public class MenuController {

    @Autowired
    private AppEleService appEleService;

    /**
     * 获取菜单
     * @param appCode 对应到当前app的appCode
     */
    public List<EleVo> getMenu (@RequestHeader("App-Code") String appCode, @RequestHeader("Role-Id") String roleId) {
        return appEleService.getApplicationMenu(appCode, roleId);
    }

}
