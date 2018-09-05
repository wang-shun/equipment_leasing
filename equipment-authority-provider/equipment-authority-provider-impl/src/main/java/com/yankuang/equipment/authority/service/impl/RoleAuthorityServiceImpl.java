package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleAuthorityMapper;
import com.yankuang.equipment.authority.model.RoleAuthority;
import com.yankuang.equipment.authority.service.RoleAuthorityService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;

    public Boolean create(RoleAuthority t) {
        return roleAuthorityMapper.create(t);
    }

    public RoleAuthority findByRoleAndAuthorityCodes(Map map) {
        return roleAuthorityMapper.findByRoleAndAuthorityCodes(map);
    }

    public boolean deleteByAuthorityCodes(List<String> codes) {
        return roleAuthorityMapper.deleteByAuthorityCodes(codes);
    }

    public boolean deleteByRoleCodes(List<String> codes) {
        return roleAuthorityMapper.deleteByRoleCodes(codes);
    }

}
