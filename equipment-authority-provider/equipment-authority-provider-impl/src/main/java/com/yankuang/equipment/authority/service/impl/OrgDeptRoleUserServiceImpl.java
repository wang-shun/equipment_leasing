package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrgDeptRoleUserMapper;
import com.yankuang.equipment.authority.model.OrgDeptRoleUser;
import com.yankuang.equipment.authority.service.OrgDeptRoleUserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class OrgDeptRoleUserServiceImpl implements OrgDeptRoleUserService{

    @Autowired
    private OrgDeptRoleUserMapper orgDeptRoleUserMapper;

    public List<OrgDeptRoleUser> getAll(OrgDeptRoleUser orgDeptRoleUser){
        return orgDeptRoleUserMapper.getAll(orgDeptRoleUser);
    }
}
