package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleMapper;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RoleService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    public Role getById(Long id) {
        return roleMapper.findById(id);
    }

    public boolean update(Role role){
        return roleMapper.update(role);
    }

    public boolean create(Role role){
        return roleMapper.create(role);
    }

    public boolean delete(Long id){
        return  roleMapper.delete(id);
    }

    public Role findByName(String name){
        return roleMapper.findByName(name);
    }

    public List<Role> getAll( ){
        return roleMapper.getAll( );
    }

    public Paging<Role> paging(Integer page, Integer size, Role role){
        Integer offset = (page - 1) * size;
        Integer limit = size;
        return  roleMapper.paging(offset, limit, role);
    }

    public Role findRoles(Long roleId){
        return roleMapper.findRoles(roleId);
    }
}
