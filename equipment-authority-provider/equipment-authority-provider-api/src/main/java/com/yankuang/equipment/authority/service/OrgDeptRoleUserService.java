package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.OrgDeptRoleUser;
import io.terminus.common.model.Paging;

import java.util.List;

public interface OrgDeptRoleUserService {
    List<OrgDeptRoleUser> getAll(OrgDeptRoleUser orgDeptRoleUser);
}
