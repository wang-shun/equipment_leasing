package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.OrgDeptRoleUser;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrgDeptRoleUserMapper extends MyBatisDao<OrgDeptRoleUser> {
    public List<OrgDeptRoleUser> getAll(){
        return this.sqlSession.selectList("getAll");
    }
}
