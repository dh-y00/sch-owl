package com.sch.owl.controller;

import com.sch.owl.config.web.GlobalDeal;
import com.sch.owl.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public LoginUserInfoVo getInfo() {
        LoginUserInfo user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        LoginUserInfoVo loginUserInfoVo = new LoginUserInfoVo();
        loginUserInfoVo.setUser(user);
        loginUserInfoVo.setRoles(roles);
        loginUserInfoVo.setPermissions(permissions);
        return loginUserInfoVo;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    @GlobalDeal
    public List<RouterVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<MenuVo> menuTreeByUserId = menuService.selectMenuTreeByUserId(userId);
        List<RouterVo> routerVos = menuService.buildMenus(menuTreeByUserId);
        return routerVos;
    }
}
