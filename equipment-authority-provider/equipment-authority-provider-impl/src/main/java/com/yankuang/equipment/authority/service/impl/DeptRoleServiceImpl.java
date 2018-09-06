package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptRoleMapper;
import com.yankuang.equipment.authority.model.DeptRole;
import com.yankuang.equipment.authority.service.DeptRoleService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 关联表.
 */
@Service
@RpcProvider
public class DeptRoleServiceImpl implements DeptRoleService {

    @Autowired
    DeptRoleMapper deptRoleMapper;


    public Boolean create(DeptRole t) {
        return deptRoleMapper.create(t);
    }

    public DeptRole findByDeptCodeAndRoleCode(Map map) {
        return deptRoleMapper.findByDeptCodeAndRoleCode(map);
    }

    public Boolean deleteByDeptCodeAndRoleCode(Map map) {
        return deptRoleMapper.deleteByDeptCodeAndRoleCode(map);
    }

    public boolean deleteByRoleCodes(List<String> codes) {
        return deptRoleMapper.deleteByRoleCodes(codes);
    }

    public boolean deleteByDeptCodes(List<String> codes) {
        return deptRoleMapper.deleteByDeptCodes(codes);
    }


}
