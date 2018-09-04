package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptRole;

import java.util.List;
import java.util.Map;

public interface DeptRoleService {

    Boolean create(DeptRole t);

    DeptRole findByDeptCodeAndRoleCode(Map map);

    Boolean deleteByDeptCode(String code);

    Boolean deleteByRoleCode(String code);

    List<DeptRole> findByRoleCode(String code);

    List<DeptRole> findByDeptCode(String code);

}
