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

    public Boolean create(RoleUser roleUser) {
        return roleUserMapper.create(roleUser);
    }

    public Boolean deleteByUserId(Long id) {
        return roleUserMapper.deleteByUserId(id);
    }

    public Boolean deleteByRoleId(Long id) {
        return roleUserMapper.deleteByRoleId(id);
    }

    public List<RoleUser> findByUserId(Long id) {
        return roleUserMapper.findByUserId(id);
    }

    public List<RoleUser> findByRoleId(Long id) {
        return roleUserMapper.findByRoleId(id);
    }
}
