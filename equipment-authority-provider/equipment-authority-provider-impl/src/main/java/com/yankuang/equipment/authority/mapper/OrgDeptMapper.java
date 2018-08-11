package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.OrgDept;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrgDeptMapper extends MyBatisDao<OrgDept> {
    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedelOD"), id) == 1;
    }

    public Long findOrgDept(OrgDept orgDept){
        return this.sqlSession.selectOne("findOrgDept",orgDept);

    }

    public List<Long> findDeptId (Long organizationId){
        return this.sqlSession.selectList("findDeptId",organizationId);
    }
}
