package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.AuthorityGroupMappingMapper;
import com.yankuang.equipment.authority.model.AuthorityGroupMapping;
import com.yankuang.equipment.authority.service.AuthorityGroupMappingService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RpcProvider
public class AuthorityGroupMappingServiceImpl implements AuthorityGroupMappingService {

    @Autowired
    AuthorityGroupMappingMapper authorityGroupMappingMapper;

    public AuthorityGroupMapping findById(Long id) {
        return authorityGroupMappingMapper.findById(id);
    }

    public Boolean create(AuthorityGroupMapping t) {
        return authorityGroupMappingMapper.create(t);
    }

    public AuthorityGroupMapping selectByAuthorityIdAndGroupId(Map map) {
        return authorityGroupMappingMapper.selectByAuthorityIdAndGroupId(map);
    }
}
