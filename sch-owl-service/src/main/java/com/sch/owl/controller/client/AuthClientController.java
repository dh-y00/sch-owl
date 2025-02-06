package com.sch.owl.controller.client;

import com.rdrk.rsf.framework.utils.string.StringUtils;
import com.sch.owl.config.web.GlobalDeal;
import com.sch.owl.model.LoginUser;
import com.sch.owl.params.ClientGetUserParam;
import com.sch.owl.properties.TokenProperties;
import com.sch.owl.response.TokenConfigResponse;
import com.sch.owl.response.UserPostResponse;
import com.sch.owl.service.TokenService;
import com.sch.owl.service.client.DeptClientService;
import com.sch.owl.service.client.UserRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inner/remote")
public class AuthClientController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private DeptClientService deptOtherService;

    @Autowired
    private UserRemoteService userRemoteService;

    @PostMapping("/getUserByToken")
    @GlobalDeal
    public LoginUser getUserByToken(@RequestBody ClientGetUserParam token) {
        LoginUser loginUser = tokenService.getLoginUser(token.getToken());
        if (StringUtils.isNotNull(loginUser) && token.isNeedVerify()) {
            tokenService.verifyToken(loginUser);
        }
        return loginUser;
    }

    @PostMapping("/getTokenConfig")
    @GlobalDeal
    public TokenConfigResponse getTokenConfig() {
        TokenConfigResponse tokenConfigResponse = new TokenConfigResponse();
        tokenConfigResponse.setHeaderTokenName(tokenProperties.getHeader());
        return tokenConfigResponse;
    }

    @PostMapping("/getDeptIdAndChildDeptId")
    @GlobalDeal
    public List<Long> getDeptIdAndChildDeptId(@RequestParam(name = "deptId") Long deptId) {
        return deptOtherService.getDeptIdAndChildDeptId(deptId);
    }

    @PostMapping("/getDeptIdByRole")
    @GlobalDeal
    public List<Long> getDeptIdByRole(@RequestParam(name = "roleId") Long roleId) {
        return deptOtherService.getDeptIdByRole(roleId);
    }

    @PostMapping("/getPostCodeByUserId")
    @GlobalDeal
    public List<UserPostResponse> getPostCodeByUserId(@RequestParam(name = "userId") Long userId) {
        return userRemoteService.getPostCodeByUserId(userId);
    }


}
