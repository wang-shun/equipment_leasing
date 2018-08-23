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

    public Boolean delete(Long id) {
        return userMapper.delete(id);
    }

    public Boolean update(User user) {
        return userMapper.update(user);
    }

    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public User findByAccount(String name) {
        return userMapper.findByAccount(name);
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }


    public PageInfo<Map> findByPage(Integer page, Integer size, Map map) {
        PageHelper.startPage(page, size);
        List<Map> maps = userMapper.list(map);
        PageInfo<Map> pageInfo = new PageInfo<Map>(maps);
        return pageInfo;
    }

    public Boolean stop(Long id) {
        return userMapper.stop(id);
    }

    public Boolean start(Long id) {
        return userMapper.start(id);
    }

}
