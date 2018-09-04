package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptRoleMapper {

    Boolean create(DeptRole t);

    DeptRole findByDeptCodeAndRoleCode(Map map);

    Boolean deleteByDeptCode(String code);

    Boolean deleteByRoleCode(String code);

    List<DeptRole> findByRoleCode(String code);

    List<DeptRole> findByDeptCode(String code);

}
