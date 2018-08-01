package com.yankuang.equipment.authority.mapper;

import io.terminus.common.mysql.dao.MyBatisDao;
import com.yankuang.equipment.authority.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapper extends MyBatisDao<User> {
    public User login(String userName) {
        return getSqlSession().selectOne("login", userName);
    }


}
