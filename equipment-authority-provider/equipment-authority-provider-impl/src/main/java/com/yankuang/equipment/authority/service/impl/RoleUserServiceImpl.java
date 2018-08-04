package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleUserMapper;
import com.yankuang.equipment.authority.model.RoleUser;
import com.yankuang.equipment.authority.service.RoleUserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    RoleUserMapper roleUserMapper;

    public boolean create(RoleUser roleAuthority) {
        return roleUserMapper.create(roleAuthority);
    }

    public List<RoleUser> findByUserId(Long userId) {
        return roleUserMapper.findByUserId(userId);
    }
}
