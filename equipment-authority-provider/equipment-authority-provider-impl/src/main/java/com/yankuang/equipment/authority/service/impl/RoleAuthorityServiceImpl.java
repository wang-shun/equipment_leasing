package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleAuthorityMapper;
import com.yankuang.equipment.authority.model.RoleAuthority;
import com.yankuang.equipment.authority.service.RoleAuthorityService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;

    public boolean create(RoleAuthority roleAuthority) {
        return roleAuthorityMapper.create(roleAuthority);
    }

    public List<RoleAuthority> findByRoleId(Long roleId) {
        return roleAuthorityMapper.findByRoleId(roleId);
    }


}
