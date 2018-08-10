package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.OrgDeptRoleUser;
import io.terminus.common.model.Paging;

import java.util.List;

public interface OrgDeptRoleUserService {

    Paging<OrgDeptRoleUser> getAll(int pageSize, int pageNum, OrgDeptRoleUser orgDeptRoleUser);

    List<OrgDeptRoleUser> getAll(OrgDeptRoleUser orgDeptRoleUser);
}
