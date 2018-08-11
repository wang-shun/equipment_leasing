package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.OrgDepartment;
import com.yankuang.equipment.authority.model.OrgDeptRoleUser;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeptOrganizationMapper extends MyBatisDao<OrgDepartment> {
    public List<OrgDepartment> deptOrgGetAll(OrgDepartment orgDepartment){
        return this.sqlSession.selectList("deptOrgGetAll",orgDepartment);
    }
}
