package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.UserMapper;
import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RpcProvider(version = "0.0.1")
public class UserServiceImpl implements UserService {

    //    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    public User login(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return null;
        }

        User loginUser = userMapper.login(userName);
        if (loginUser != null && password.equals(loginUser.getPassword())) {
            System.out.println(loginUser);
            return loginUser;
        }
        return null;
    }

    public User getById(Long id) {
        return userMapper.findById(id);
    }

    public String healthCheck() {
        return "OK";
    }
}
