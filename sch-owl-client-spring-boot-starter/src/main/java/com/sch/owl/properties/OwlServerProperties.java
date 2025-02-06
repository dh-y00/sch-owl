package com.sch.owl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sch.owl.server")
public class OwlServerProperties {

    /**
     * 权限服务跟域名
     */
    private String url;

    /**
     * 退出登录接口
     */
    private String logoutUrl = "/logout";

    /**
     * 获取到用户信息接口
     */
    private String userByTokenUrl = "/inner/remote/getUserByToken";

    /**
     * 获取token配置信息接口
     */
    private String tokenConfigUrl = "/inner/remote/getTokenConfig";

    /**
     * 获取部门id和子部门id接口
     */
    private String deptIdAndChildDeptIdUrl = "/inner/remote/getDeptIdAndChildDeptId";

    /**
     * 根据角色获取部门ID
     */
    private String deptIdByRoleUrl = "/inner/remote/getDeptIdByRole";

    /**
     * 根据用户id获取岗位编码
     */
    private String postCodeByUserIdUrl = "/inner/remote/getPostCodeByUserId";

    /**
     * 获取到对应部门的拓展字段
     */
    private String extraByDeptIdUrl = "/inner/remote/getExtraByDeptId";

}
