package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Role;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleMapper extends MyBatisDao<Role> {

    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedel"), id) == 1;
    }

    public Role findByName(String name) {
        return getSqlSession().selectOne("findByNameR", name);
    }
    public List<Role> getAll( ){
        return getSqlSession().selectList("findAllR");
    }
}
