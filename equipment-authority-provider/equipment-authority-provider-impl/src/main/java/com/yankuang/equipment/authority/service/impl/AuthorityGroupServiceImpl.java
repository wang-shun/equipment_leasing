package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.AuthorityGroupMapper;
import com.yankuang.equipment.authority.model.AuthorityGroup;
import com.yankuang.equipment.authority.service.AuthorityGroupService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class AuthorityGroupServiceImpl implements AuthorityGroupService {

    @Autowired
    AuthorityGroupMapper authorityGroupMapper;

    public AuthorityGroup findByName(String name) {
        return authorityGroupMapper.findByName(name);
    }

    public Boolean create(AuthorityGroup authorityGroup) {
        return authorityGroupMapper.create(authorityGroup);
    }

}
