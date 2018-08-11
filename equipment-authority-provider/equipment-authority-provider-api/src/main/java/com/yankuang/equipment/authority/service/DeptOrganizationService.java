package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.OrgDepartment;
import com.yankuang.equipment.authority.model.OrgDeptRoleUser;

import java.util.List;

public interface DeptOrganizationService {
    List<OrgDepartment> deptOrgAll(OrgDepartment orgDepartment);
}
