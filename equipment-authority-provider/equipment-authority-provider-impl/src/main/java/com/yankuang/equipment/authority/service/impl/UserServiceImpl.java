package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.UserMapper;
import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider(version = "0.0.1")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String name) {
        return userMapper.login(name);
    }

    public User findByCode(String code) {
        return userMapper.findByCode(code);
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public boolean create(User user){
        return userMapper.create(user);
    }

    public String healthCheck() {
        return "OK";
    }

    public Paging<User> paging(Integer offset, Integer limit, User user) {
        return userMapper.paging(offset, limit, user);
    }

    public Boolean delete(Long id) {
        return userMapper.delete(id);
    }

    public Boolean update(User user) {
        return userMapper.update(user);
    }

}
