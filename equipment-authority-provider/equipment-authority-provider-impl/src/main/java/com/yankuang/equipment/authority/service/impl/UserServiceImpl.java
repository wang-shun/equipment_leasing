package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.UserMapper;
import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider(version = "0.0.1")
public class UserServiceImpl implements UserService {

    //    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    public User login(String userName, String password) {
        return userMapper.login(userName);
    }

    public User getById(Long id) {
        return userMapper.findById(id);
    }

    public boolean create(User user){
        return userMapper.create(user);
    }

    public String healthCheck() {
        return "OK";
    }
}
