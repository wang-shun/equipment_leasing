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

    public Boolean deleteByDeptCode(String code) {
        return deptRoleMapper.deleteByDeptCode(code);
    }

    public Boolean deleteByRoleCode(String code) {
        return deptRoleMapper.deleteByRoleCode(code);
    }

    public List<DeptRole> findByRoleCode(String code) {
        return deptRoleMapper.findByRoleCode(code);
    }

    public List<DeptRole> findByDeptCode(String code) {
        return deptRoleMapper.findByDeptCode(code);
    }

}
