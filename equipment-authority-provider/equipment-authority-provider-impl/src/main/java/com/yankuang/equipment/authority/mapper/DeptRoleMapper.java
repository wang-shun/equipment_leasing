package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptRole;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class DeptRoleMapper extends MyBatisDao<DeptRole> {

    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedelDR"), id) == 1;
    }
}
