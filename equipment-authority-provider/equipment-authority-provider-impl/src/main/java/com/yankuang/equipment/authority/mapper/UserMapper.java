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

    public Long findUserName(String account){
        return getSqlSession().selectOne("findUserName",account);
    }

    public Long findUserIds(String account){
        return getSqlSession().selectOne("findUserIds",account);
    }

    public Boolean closeStatusUser(Long id){
        return this.getSqlSession().update("closeStatusUser",id) == 1;
    }

    public Boolean openStatusUser(Long id){
        return this.getSqlSession().update("openStatusUser",id) == 1;
    }

    public Boolean updateAccount(User user){
        return this.getSqlSession().update("updateAccount",user) == 1;
    }

    public Long findUserAccount(String name){
        return this.getSqlSession().selectOne("findUserAccount",name);
    }

    public Long findUserSex(Byte sex){
        return this.getSqlSession().selectOne("findUserSex",sex);
    }
}
