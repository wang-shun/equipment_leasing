package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.OrgDept;

public interface OrgDeptService {

    boolean add(OrgDept orgDept);

    boolean delById(Long id);
}
