package com.yankuang.equipment.authority.mapper;

import io.terminus.common.mysql.dao.MyBatisDao;
import com.yankuang.equipment.authority.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapper extends MyBatisDao<User> {
    public User login(String name) {
        return getSqlSession().selectOne("login", name);
    }

    public User findByCode(String code){
        return getSqlSession().selectOne("findByCode", code);
    }

}
