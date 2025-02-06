package com.sch.owl.token;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.ITokenConfig;
import com.sch.owl.constant.Constants;

import javax.servlet.http.HttpServletRequest;

public class TokenDeal {

    private ITokenConfig tokenConfig;

    public TokenDeal(ITokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(tokenConfig.getHeaderTokenName());
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

}
