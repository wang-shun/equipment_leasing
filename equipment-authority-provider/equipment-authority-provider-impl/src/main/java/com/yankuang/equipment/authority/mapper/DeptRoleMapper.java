package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptRoleMapper {

    Boolean create(DeptRole t);

    DeptRole findByDeptCodeAndRoleCode(Map map);

    Boolean deleteByDeptCodeAndRoleCode(Map map);

    boolean deleteByRoleCodes(List<String> codes);

    boolean deleteByDeptCodes(List<String> codes);

}
