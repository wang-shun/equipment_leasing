package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.AuthorityGroup;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorityGroupMapper extends MyBatisDao<AuthorityGroup> {

    public AuthorityGroup findByName(String name) {
        return getSqlSession().selectOne("findByName", name);
    }

}
