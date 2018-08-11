package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.AuthorityGroup;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorityGroupMapper extends MyBatisDao<AuthorityGroup> {

    public AuthorityGroup findByName(String name) {
        return getSqlSession().selectOne("com.yankuang.equipment.authority.mapper.AuthorityGroupMapper.findByName", name);
    }

    public Boolean create(AuthorityGroup t) {
        return getSqlSession().insert("com.yankuang.equipment.authority.mapper.AuthorityGroupMapper.create", t) == 1;
    }

}
