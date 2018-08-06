package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.model.Dept;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeptMapper extends MyBatisDao<Dept> {

    public Boolean updatedel(Long id) {
        return this.sqlSession.insert(this.sqlId("updatedel"), id) == 1;
    }

    public Dept findByName(String name) {
        return getSqlSession().selectOne("findByNameD", name);
    }
    public List<Dept> getAll( ){
        return getSqlSession().selectList("findAllD");
    }
    public List<String> getName(){
        return getSqlSession().selectList("deptFindName");
    }

    public Long getId(String name){
        return getSqlSession().selectOne("getId");
    }
}
