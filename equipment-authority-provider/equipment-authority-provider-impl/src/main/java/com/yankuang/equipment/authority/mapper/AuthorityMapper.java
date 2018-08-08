package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Authority;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorityMapper extends MyBatisDao<Authority> {
    public Authority findByName(String name) {
        return getSqlSession().selectOne("findByName", name);
    }
    public List<Authority> findAll( ){
        return getSqlSession().selectList("com.yankuang.equipment.authority.mapper.AuthorityMapper.findAll");
    }

}
