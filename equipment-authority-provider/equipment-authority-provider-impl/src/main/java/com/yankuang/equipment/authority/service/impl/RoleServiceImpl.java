package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleMapper;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RolService;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class RoleServiceImpl implements RolService{
    @Autowired
    RoleMapper roleMapper;

    @Reference
    UuidUtils uuidUtils;

    public Role getById(Long id) {
        return roleMapper.findById(id);
    }

    public boolean update(Role role){
        return roleMapper.update(role);
    }

    public boolean add(Role role){
        role.setCode(uuidUtils.newUuid());
        role.setCreateBy("小狼");
        role.setUpdateBy("小狼");
        return roleMapper.create(role);
    }

    public boolean del(Long id){
        return  roleMapper.updatedel(id);
    }

    public List<Role> getAll( List<Long> ids){
        return  roleMapper.findByIds(ids);
    }

    public Role getByName(String name){
        return roleMapper.findByName(name);
    }

    public List<Role> getAll( ){
        return roleMapper.getAll( );
    }

}
