package com.sch.owl.permission.aspect;

import com.rdrk.rsf.framework.utils.string.Convert;
import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.HttpRemoteUpmsServe;
import com.sch.owl.config.security.context.PermissionContextHolder;
import com.sch.owl.model.LoginUser;
import com.sch.owl.model.LoginUserInfo;
import com.sch.owl.model.RoleInfoVo;
import com.sch.owl.permission.annotation.DataScopeNew2;
import com.sch.owl.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据过滤处理
 *
 * @author ruoyi
 */
@Aspect
public class DataScopeNew2Aspect
{
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Autowired
    private HttpRemoteUpmsServe remoteUpmsServe;

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScopeNew2 controllerDataScope) throws Throwable
    {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScopeNew2 controllerDataScope)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            LoginUserInfo user = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(user) && !user.isAdmin())
            {
                String permission = StringUtils.defaultIfEmpty(controllerDataScope.permission(), PermissionContextHolder.getContext());
                dataScopeFilter(joinPoint, user, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias(), permission);
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     * @param permission 权限字符
     */
    public void dataScopeFilter(JoinPoint joinPoint, LoginUserInfo user, String deptAlias, String userAlias, String permission)
    {
        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<String>();

        for (RoleInfoVo role : user.getRoles())
        {
            String dataScope = role.getDataScope();
            if (!DATA_SCOPE_CUSTOM.equals(dataScope) && conditions.contains(dataScope))
            {
                continue;
            }
            if (StringUtils.isNotEmpty(permission) && StringUtils.isNotEmpty(role.getPermissions())
                    && !StringUtils.containsAny(role.getPermissions(), Convert.toStrArray(permission)))
            {
                continue;
            }
            if (DATA_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                conditions.add(dataScope);
                break;
            }
            else if (DATA_SCOPE_CUSTOM.equals(dataScope))
            {
                List<Long> deptIdFromRoleDept = remoteUpmsServe.getDeptIdByRole(role.getRoleId());

                String join = StringUtils.join(deptIdFromRoleDept, ",");
                sqlString.append(StringUtils.format(" OR {}.dept_id IN ("  + join + ")", deptAlias));
                //sqlString.append(StringUtils.format(" OR dept_id IN ("  + join + ")", deptAlias));
            }
            else if (DATA_SCOPE_DEPT.equals(dataScope))
            {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
                //sqlString.append(StringUtils.format(" OR dept_id = {} ", deptAlias, user.getDeptId()));
            }
            else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))
            {
                List<Long> deptIdFromDept = DeptCache.getDeptIdAndChildDeptId(user.getDeptId());
                String join = StringUtils.join(deptIdFromDept, ",");
                sqlString.append(StringUtils.format(" OR {}.dept_id IN (" + join + ")", deptAlias));
                //sqlString.append(StringUtils.format(" OR dept_id IN (" + join + ")", deptAlias));
            }
            else if (DATA_SCOPE_SELF.equals(dataScope))
            {
                if (StringUtils.isNotBlank(userAlias))
                {
                    sqlString.append(StringUtils.format(" OR find_in_set({}, owner) ", user.getUserId()));
                    //sqlString.append(StringUtils.format(" OR user_id = {} ", userAlias, user.getUserId()));
                }
                else
                {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(StringUtils.format(" OR {}.dept_id = 0 ", deptAlias));
                    //sqlString.append(StringUtils.format(" OR dept_id = 0 ", deptAlias));
                }
            }
            conditions.add(dataScope);
        }

        // 多角色情况下，所有角色都不包含传递过来的权限字符，这个时候sqlString也会为空，所以要限制一下,不查询任何数据
        if (StringUtils.isEmpty(conditions))
        {
            sqlString.append(StringUtils.format(" OR {}.dept_id = 0 ", deptAlias));
        }

        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BasePojo)
            {
                BasePojo baseEntity = (BasePojo) params;
                baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BasePojo)
        {
            BasePojo baseEntity = (BasePojo) params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
