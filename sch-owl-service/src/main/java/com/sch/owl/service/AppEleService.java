package com.sch.owl.service;

import com.rdrk.rsf.framework.constant.SystemConstant;
import com.sch.owl.constant.EleConstant;
import com.sch.owl.entity.OwlApplicationEle;
import com.sch.owl.pojo.vo.EleVo;
import com.sch.owl.service.db.OwlApplicationEleDbService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 service 层
 */
@Service
public class AppEleService {

    @Autowired
    private OwlApplicationEleDbService applicationEleDbService;

    /**
     * 获得当前应用菜单
     */
    public List<EleVo> getApplicationMenu(String appCode, String roleId) {
        // todo 统一校验角色和应用
        String[] split = roleId.split(",");
        List<Long> roleIds = new ArrayList<>();
        for (String s : split) {
            roleIds.add(Long.parseLong(s));
        }

        List<OwlApplicationEle> owlApplicationEles = applicationEleDbService.queryByAppCodeAndType(appCode, roleIds, EleConstant.EleTypeEnum.CATALOG.getCode(), EleConstant.EleTypeEnum.MENU.getCode());
        return buildEleVo(owlApplicationEles);
    }


    private List<EleVo> buildEleVo(List<OwlApplicationEle> owlApplicationEles) {
        if (owlApplicationEles.isEmpty()) {
            return Collections.emptyList();
        }
        List<EleVo> eleVos = new ArrayList<>();
        // 所有顶层父元素构建
        return owlApplicationEles.stream().filter(appEle -> StringUtils.equals(appEle.getAncestors(), "0")).map(appEle -> {
            EleVo eleVo = new EleVo();
            eleVo.setName(appEle.getName());
            eleVo.setIcon(appEle.getIcon());
            eleVo.setIsFrame(SystemConstant.YesAndNoEnum.isYes(appEle.getIsFrame()));
            eleVo.setPath(appEle.getUrl());
            eleVo.setOrder(appEle.getOrder());
            eleVo.setVisible(SystemConstant.YesAndNoEnum.isYes(appEle.getVisible()));
            eleVo.setType(appEle.getType());
            eleVo.setChildren(buildChildEleVo(owlApplicationEles, appEle.getId()));
            return eleVo;
        }).collect(Collectors.toList());
    }

    private List<EleVo> buildChildEleVo(List<OwlApplicationEle> owlApplicationEles, Long appEleId) {
        return owlApplicationEles.stream().filter(appEle -> Objects.equals(appEle.getParentId(), appEleId))
                .map(appEle -> {
            EleVo eleVo = new EleVo();
            eleVo.setName(appEle.getName());
            eleVo.setIcon(appEle.getIcon());
            eleVo.setIsFrame(SystemConstant.YesAndNoEnum.isYes(appEle.getIsFrame()));
            eleVo.setPath(appEle.getUrl());
            eleVo.setOrder(appEle.getOrder());
            eleVo.setVisible(SystemConstant.YesAndNoEnum.isYes(appEle.getVisible()));
            eleVo.setType(appEle.getType());
            eleVo.setChildren(buildChildEleVo(owlApplicationEles, appEle.getId()));
            return eleVo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据 app code 获取当前app下所有的元素
     * @param appCode
     * @return
     */
    public List<EleVo> getAppAllEleTree(String appCode) {
        List<OwlApplicationEle> owlApplicationEles = applicationEleDbService.queryByAppCode(appCode);
        return buildEleVo(owlApplicationEles);
    }
}
