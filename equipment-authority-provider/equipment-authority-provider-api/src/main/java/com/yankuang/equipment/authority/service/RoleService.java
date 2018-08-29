package com.yankuang.equipment.authority.service;


import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    List<Role> findByUserId(Long id);

    Role findById(Long id);

    boolean update(Role role);

    boolean create(Role role);

    boolean delete(Long id);

    Role findByName(String name);

    List<Role> findAll();

    PageInfo<Role> list(Integer page, Integer size, Map role);

}
