package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleUserMapper;
import com.yankuang.equipment.authority.model.RoleUser;
import com.yankuang.equipment.authority.service.RoleUserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    RoleUserMapper roleUserMapper;

    public Boolean create(RoleUser roleUser) {
        return roleUserMapper.create(roleUser);
    }

    public Boolean deleteByUserCode(List<String> codes) {
        return roleUserMapper.deleteByUserCode(codes);
    }

    public Boolean deleteByRoleCode(List<String> codes) {
        return roleUserMapper.deleteByRoleCode(codes);
    }

    public List<RoleUser> findByUserCode(List<String> codes) {
        return roleUserMapper.findByUserCode(codes);
    }

    public List<RoleUser> findByRoleCode(List<String> codes) {
        return roleUserMapper.findByRoleCode(codes);
    }

    public List<RoleUser> findByRoleCodeAndUserCode(Map map) {
        return roleUserMapper.findByRoleCodeAndUserCode(map);
    }


}
