package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Authority;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorityMapper extends MyBatisDao<Authority> {
    public Boolean updatedel(Long id) {
        return this.sqlSession.update(this.sqlId("updatedel"), id) == 1;
    }
    public Authority findByName(String name) {
        return getSqlSession().selectOne("findByNameA", name);
    }
    public List<Authority> getAll( ){
        return getSqlSession().selectList("findAllA");
    }
    public List<String> getName(){
        return getSqlSession().selectList("aclFindName");
    }
}
