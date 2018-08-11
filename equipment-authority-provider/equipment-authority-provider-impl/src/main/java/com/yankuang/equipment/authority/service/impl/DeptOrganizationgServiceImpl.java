package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptOrganizationMapper;
import com.yankuang.equipment.authority.model.OrgDepartment;
import com.yankuang.equipment.authority.model.OrgDeptRoleUser;
import com.yankuang.equipment.authority.service.DeptOrganizationService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RpcProvider
public class DeptOrganizationgServiceImpl implements DeptOrganizationService{

    @Autowired
    private DeptOrganizationMapper deptOrganizationMapper;

    public List<OrgDepartment> deptOrgAll(OrgDepartment orgDepartment){
        return deptOrganizationMapper.deptOrgGetAll(orgDepartment);
    }
}
