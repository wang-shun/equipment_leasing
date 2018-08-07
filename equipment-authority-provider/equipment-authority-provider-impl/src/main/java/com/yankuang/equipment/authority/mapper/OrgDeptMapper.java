package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.OrgDept;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class OrgDeptMapper extends MyBatisDao<OrgDept> {
    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedelOD"), id) == 1;
    }

    public List<OrgDept> findOrgDept(Long departmentId, Long organizationId){
        return this.sqlSession.selectList(this.sqlId("findOrgDept"), Arrays.asList(departmentId, organizationId));

    }
}
