package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.OrgDept;

import java.util.List;
import java.util.Map;

public interface OrgDeptService {

    Boolean create(OrgDept orgDept);

    Boolean deleteByOrgId(Long id);

    Boolean deleteByDeptId(Long id);

    Boolean deleteByOrgIdAndDeptId(Map map);

    Boolean update(OrgDept orgDept);

    List<OrgDept> findByOrgId(Long id);

    List<OrgDept> findByOrgIdAndDeptId(Map map);

}
