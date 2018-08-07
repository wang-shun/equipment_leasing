package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleMapper;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RoleService;
import com.yankuang.equipment.common.util.UuidUtils;
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

    public boolean add(Role role){
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

    public Paging paging(int pageSize, int pageNum, Role role){
        int maxResult = (pageNum - 1) * pageSize;
        Paging page = roleMapper.paging(maxResult, pageNum, role);
        return page;
    }

}
