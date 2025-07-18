package com.sch.owl.permission;

import com.rdrk.rsf.framework.utils.ApplicationContextUtils;
import com.sch.owl.model.LoginUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 权限的代码片段
 */
public interface IDataScopeSqlFragment {

    /**
     * 构建一个权限字符串的代码片段
     * @param deptAlias 部门重命名
     * @param userAlias 用户重命名
     * @param user 当前登录用户信息
     * @return 返回代码片段
     */
    String getSql(String deptAlias, String userAlias, LoginUser user, String dataScope);

    /**
     * 代码片段code
     * @return 返回代码片段code
     */
    String code();

    static String getDataScopeSqlFragment(String code, String deptAlias, String userAlias, LoginUser user, String dataScope) {
        if(StringUtils.isBlank(code)) {
            return null;
        }
        Map<String, IDataScopeSqlFragment> beansOfType = ApplicationContextUtils.getBeansOfType(IDataScopeSqlFragment.class);
        for (IDataScopeSqlFragment value : beansOfType.values()) {
            if (value.code().equals(code)) {
                return value.getSql(deptAlias, userAlias, user, dataScope);
            }
        }
        return null;
    }
}
