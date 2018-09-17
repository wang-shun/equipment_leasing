package com.yankuang.equipment.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.mapper.UserMapper;
import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean create(User user) {
        return userMapper.create(user);
    }

    public Boolean delete(List<String> codes) {
        return userMapper.delete(codes);
    }

    public Boolean update(User user) {
        return userMapper.update(user);
    }

    public List<User> findByName(String name) {
        return userMapper.findByName(name);
    }

    public User findByAccount(String name) {
        return userMapper.findByAccount(name);
    }

    public User findByCode(String code) {
        return userMapper.findByCode(code);
    }


    public PageInfo<User> findByPage(Integer page, Integer size, Map map) {
        PageHelper.startPage(page, size);
        List<User> maps = userMapper.list(map);
        PageInfo<User> pageInfo = new PageInfo<User>(maps);
        return pageInfo;
    }

    public Boolean stop(String code) {
        return userMapper.stop(code);
    }

    public Boolean start(String code) {
        return userMapper.start(code);
    }

}
