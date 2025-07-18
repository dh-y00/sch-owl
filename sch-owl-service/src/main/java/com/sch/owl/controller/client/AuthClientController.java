package com.sch.owl.controller.client;

import com.sch.owl.application.service.auth.TokenAppService;
import com.sch.owl.config.web.GlobalDeal;
import com.sch.owl.model.UserDetail;
import com.sch.owl.params.ClientGetUserParam;
import com.sch.owl.response.TokenConfigResponse;
import com.sch.owl.response.UserPostResponse;
import com.sch.owl.service.client.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/inner/remote")
public class AuthClientController {

    @Autowired
    private DeptClientService deptOtherService;

//    @Autowired
//    private UserRemoteService userRemoteService;

    @Autowired
    private TokenAppService tokenAppService;

    @PostMapping("/getUserByToken")
    @GlobalDeal
    public UserDetail getUserByToken(@RequestBody ClientGetUserParam token) {
        UserDetail userDetail = tokenAppService.getLoginUserByToken(token.getToken());
        if (Objects.nonNull(userDetail) && token.isNeedVerify()) {
            tokenAppService.verifyUserDetail(userDetail);
        }
        return userDetail;
    }

    @PostMapping("/getTokenConfig")
    @GlobalDeal
    public TokenConfigResponse getTokenConfig() {
        TokenConfigResponse tokenConfigResponse = new TokenConfigResponse();
        tokenConfigResponse.setHeaderTokenName(tokenAppService.getHeaderTokenName());
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
//        return userRemoteService.getPostCodeByUserId(userId);
        return Collections.emptyList();
    }


}
