package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.User;

import java.util.Map;

public interface UserService {

    Boolean create(User user);

    Boolean delete(Long id);

    Boolean update(User user);

    User findByName(String name);

    User findByAccount(String account);

    User findById(Long account);

    PageInfo<Map> findByPage(Integer page, Integer size, Map user);

    Boolean stop(Long id);

    Boolean start(Long id);

}
