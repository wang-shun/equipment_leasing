package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Boolean create(User user);

    Boolean delete(List<String> codes);

    Boolean update(User user);

    User findByName(String name);

    User findByAccount(String account);

    User findByCode(String code);

    PageInfo<Map> findByPage(Integer page, Integer size, Map user);

    Boolean stop(String code);

    Boolean start(String code);

}
