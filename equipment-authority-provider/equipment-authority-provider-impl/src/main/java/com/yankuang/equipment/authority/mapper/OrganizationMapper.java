package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Organization;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationMapper extends MyBatisDao<Organization> {

    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedel"), id) == 1;
    }

    public Organization findByName(String name) {
        return getSqlSession().selectOne("findByNameO", name);
    }
    public List<Organization> getAll( ){
        return getSqlSession().selectList("findAllO");
    }
    public List<String> getName(){
        return getSqlSession().selectList("orgFindName");
    }
}