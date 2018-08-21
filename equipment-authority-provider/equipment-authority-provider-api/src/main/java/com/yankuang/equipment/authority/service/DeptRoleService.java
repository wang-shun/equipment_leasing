package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptRole;

import java.util.List;
import java.util.Map;

public interface DeptRoleService {

    Boolean create(DeptRole t);

    DeptRole selectByDeptIdAndRoleId(Map map);

    Boolean deleteByDeptId(Long id);

    Boolean deleteByRoleId(Long id);

    List<DeptRole> findByRoleId(Long roleId);

    List<DeptRole> findByDeptId(Long deptId);

}
