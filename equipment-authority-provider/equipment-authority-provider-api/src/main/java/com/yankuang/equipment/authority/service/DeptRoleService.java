package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptRole;

import java.util.List;
import java.util.Map;

public interface DeptRoleService {

    Boolean create(DeptRole t);

    DeptRole selectByDeptIdAndRoleId(Map map);
    boolean delById(Long id);
    List<Long> findRoleId(Long deptId);
}
