package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.RoleMapper;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RolService;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class RoleServiceImpl implements RolService{
    @Autowired
    RoleMapper roleMapper;

    public Role getById(Long id) {
        return roleMapper.findById(id);
    }

    public boolean update(Role role){
        return roleMapper.update(role);
    }

    public boolean add(Role role){
        role.setCode(UuidUtils.newUuid());
        role.setCreateBy("小狼");//TODO 由于用户功能暂未开发完，先写死，后期改
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

    public Paging findpage(int pageSize, int pageNum, Role role){
        int maxResult = (pageNum - 1) * pageSize;
        Paging page = roleMapper.paging(maxResult, pageNum, role);
        return page;
    }

}
