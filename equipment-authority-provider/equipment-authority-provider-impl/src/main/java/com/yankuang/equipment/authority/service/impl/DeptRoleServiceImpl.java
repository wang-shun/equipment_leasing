package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptRoleMapper;
import com.yankuang.equipment.authority.model.DeptRole;
import com.yankuang.equipment.authority.service.DeptRoleService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RpcProvider
public class DeptRoleServiceImpl implements DeptRoleService {

    @Autowired
    DeptRoleMapper deptRoleMapper;

    public Boolean create(DeptRole t) {
        return deptRoleMapper.create(t);
    }

    public DeptRole selectByDeptIdAndRoleId(Map map) {
        return deptRoleMapper.selectByDeptIdAndRoleId(map);
    }
}
