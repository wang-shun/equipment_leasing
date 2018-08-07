package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.OrgDept;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class OrgDeptMapper extends MyBatisDao<OrgDept> {
    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedelOD"), id) == 1;
    }
}
