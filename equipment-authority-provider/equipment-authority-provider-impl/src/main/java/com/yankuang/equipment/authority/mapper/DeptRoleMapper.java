package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptRoleMapper {

    Boolean create(DeptRole t);

    DeptRole selectByDeptIdAndRoleId(Map map);

    Boolean deleteByDeptId(Long id);

    Boolean deleteByRoleId(Long id);

    List<DeptRole> findByRoleId(Long deptId);

    List<DeptRole> findByDeptId(Long deptId);

}
