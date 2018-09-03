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

    public List<RoleAuthority> findByRoleCode(String code) {
        return roleAuthorityMapper.findByRoleCode(code);
    }

    public RoleAuthority findByRoleAndAuthorityCodes(Map map) {
        return roleAuthorityMapper.findByRoleAndAuthorityCodes(map);
    }

    public boolean delete(List<String> codes) {
        return roleAuthorityMapper.delete(codes);
    }
}
