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

    public Boolean deleteByUserCode(String code) {
        return roleUserMapper.deleteByUserCode(code);
    }

    public Boolean deleteByRoleCode(String code) {
        return roleUserMapper.deleteByRoleCode(code);
    }

    public List<RoleUser> findByUserCode(String code) {
        return roleUserMapper.findByUserCode(code);
    }

    public List<RoleUser> findByRoleCode(String code) {
        return roleUserMapper.findByRoleCode(code);
    }


}
