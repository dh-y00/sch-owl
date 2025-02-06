package com.sch.owl;


import com.sch.owl.params.ClientGetUserParam;
import com.sch.owl.params.SysJobLogParam;
import com.sch.owl.response.*;

import java.util.List;

/**
 * 远程调用Upms服务接口
 */
public interface IRemoteUpmsServe {

    /**
     * 根据 token 获取用户信息
     */
    <T> T getUserInfoByToken(ClientGetUserParam clientGetUserParam, Class<T> clazz);

    /**
     * 远程获取到token相关配置
     * @return 返回token配置
     */
    TokenConfigResponse getTokenConfig();

    /**
     * 获取部门id和子部门id
     * @param deptId 部门id
     * @return 当前部门和子部门id
     */
    List<Long> getDeptIdAndChildDeptId(Long deptId);

    /**
     * 获取角色对应部门id
     * @param roleId 角色id
     * @return 部门主键
     */
    List<Long> getDeptIdByRole(Long roleId);

    /**
     * 根据用户id获取岗位code
     * @param userId 用户id
     * @return 岗位 code
     */
    List<UserPostResponse> getPostCodeByUserId(Long userId);

}
