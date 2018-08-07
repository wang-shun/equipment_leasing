package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.OrgDept;
import io.terminus.common.model.Paging;

public interface OrgDeptService {

    boolean add(OrgDept orgDept);

    boolean delById(Long id);

    boolean update(OrgDept orgDept);

    OrgDept selOrgDept(Long id);

    Boolean udtOrgDept(OrgDept orgDept);

    Paging findpage(int pageSize, int pageNum, OrgDept orgDept);
}
