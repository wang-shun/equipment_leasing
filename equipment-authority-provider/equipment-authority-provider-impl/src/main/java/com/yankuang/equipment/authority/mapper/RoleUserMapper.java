package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleUser;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RoleUserMapper extends MyBatisDao<RoleUser> {

    public Boolean create(RoleUser roleUser){
        return this.sqlSession.insert(this.sqlId("create"), roleUser) == 1;
    }


    public List<RoleUser> findByUserId(Long userId){
        return this.sqlSession.selectList("findByUserId", userId);
    }
}
