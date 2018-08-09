package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Role;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleMapper extends MyBatisDao<Role> {

    public Role findByName(String name) {
        return getSqlSession().selectOne("com.yankuang.equipment.authority.mapper.RoleMapper.findByName", name);
    }
    public List<Role> getAll( ){
        return getSqlSession().selectList("com.yankuang.equipment.authority.mapper.RoleMapper.findAll");
    }

}
