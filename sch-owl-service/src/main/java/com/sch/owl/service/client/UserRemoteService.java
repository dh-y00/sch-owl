package com.sch.owl.service.client;

import com.sch.owl.response.UserPostResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserRemoteService {

    @Autowired
    private UpmsUserPostDbService upmsUserPostDbService;

    @Autowired
    private SysLandlineInfoDbService landlineInfoDbService;

    @Autowired
    private UpmsUserDbService userDbService;

    @Autowired
    private SysQiweiAccountDbService qiweiAccountDbService;

    @Autowired
    private UpmsUserPqcDbService userPqcDbService;

    @Autowired
    private UpmsSaleDeptDbService saleDeptDbService;

    public List<UserPostResponse> getPostCodeByUserId(Long userId) {
        List<PostVo> postVos = upmsUserPostDbService.queryPostByUserId(userId);
        if(CollectionUtils.isEmpty(postVos)) {
            return Collections.emptyList();
        }
        return postVos.stream().map(postVo -> {
            UserPostResponse userPostResponse = new UserPostResponse();
            userPostResponse.setCode(postVo.getPostCode());
            userPostResponse.setName(postVo.getPostName());
            return userPostResponse;
        }).collect(Collectors.toList());
    }

    public String getLandlineByUserId(Long userId) {
        UpmsUser upmsUser = userDbService.queryByUserId(userId);
        if(Objects.isNull(upmsUser.getCallId())) {
            return null;
        }
        SysLandlineInfo sysLandlineInfo = landlineInfoDbService.queryById(upmsUser.getCallId());
        CheckUtils.checkObjectNoNull(sysLandlineInfo, BusinessExceptionCodeEnum.ERR_2009);
        LandlineConstant.LandlineStatusEnum.checkAllocated(sysLandlineInfo.getStatus());
        return sysLandlineInfo.getNumber();

    }

    public DeptQwResponse getQwByDeptId(Long deptId) {
        SysQiweiAccount sysQiweiAccount = qiweiAccountDbService.queryByDeptId(deptId);
        if(Objects.isNull(sysQiweiAccount)) {
            return null;
        }
        DeptQwResponse deptQwResponse = new DeptQwResponse();
        deptQwResponse.setAgentId(sysQiweiAccount.getAgentId());
        deptQwResponse.setCorpId(sysQiweiAccount.getCorpId());
        deptQwResponse.setCorpSecret(sysQiweiAccount.getCorpSecret());
        
        return deptQwResponse;
    }

    public UserPqcResponse getPqcByUserId(Long userId) {
        UpmsUserPqc upmsUserPqc = userPqcDbService.queryByUserId(userId);
        if(Objects.isNull(upmsUserPqc)) {
            return null;
        }
        UserPqcResponse userPqcResponse = new UserPqcResponse();
        userPqcResponse.setQfa(upmsUserPqc.getQfa());
        userPqcResponse.setFacc(upmsUserPqc.getFacc());
        return userPqcResponse;
    }

    public SaleDeptResponse getSaleDeptByDeptId(Long deptId) {
        UpmsSaleDept upmsSaleDept = saleDeptDbService.queryByDeptId(deptId);
        if(Objects.isNull(upmsSaleDept)) {
            return null;
        }
        SaleDeptResponse saleDeptResponse = new SaleDeptResponse();
        saleDeptResponse.setSaleDeptCode(upmsSaleDept.getSaleDeptCode());
        saleDeptResponse.setSaleDeptName(upmsSaleDept.getSaleDeptName());
        return saleDeptResponse;
    }
}
