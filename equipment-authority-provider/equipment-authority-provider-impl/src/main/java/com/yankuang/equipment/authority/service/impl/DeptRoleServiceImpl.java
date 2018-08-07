package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptRoleMapper;
import com.yankuang.equipment.authority.service.DeptRoleService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class DeptRoleServiceImpl implements DeptRoleService {

    @Autowired
    DeptRoleMapper deptRoleMapper;

    public boolean delById(Long id){
        return deptRoleMapper.updatedel(id);
    }
}
