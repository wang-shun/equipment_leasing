package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptRole;

import java.util.List;
import java.util.Map;

public interface DeptRoleService {

    Boolean create(DeptRole t);

    DeptRole findByDeptCodeAndRoleCode(Map map);

    Boolean deleteByDeptCodeAndRoleCode(Map map);

    boolean deleteByRoleCodes(List<String> codes);

    boolean deleteByDeptCodes(List<String> codes);

}
