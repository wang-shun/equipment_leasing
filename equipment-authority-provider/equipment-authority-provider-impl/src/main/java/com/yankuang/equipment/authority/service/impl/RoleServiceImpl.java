package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleMapper;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RolService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class RoleServiceImpl implements RolService{
    @Autowired
    RoleMapper roleMapper;

    public Role getById(Long id) {
        return roleMapper.findById(id);
    }
}
