package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.OrgDept;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrgDeptMapper {

    Boolean create(OrgDept orgDept);

    Boolean deleteByOrgId(Long id);

    Boolean deleteByDeptId(Long id);

    Boolean deleteByOrgIdAndDeptId(Map map);

    Boolean update(OrgDept orgDept);

    List<OrgDept> findByOrgId(Long id);

    List<OrgDept> findByOrgIdAndDeptId(Map map);

}
