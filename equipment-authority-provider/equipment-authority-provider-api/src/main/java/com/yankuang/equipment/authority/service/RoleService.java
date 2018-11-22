package com.yankuang.equipment.authority.service;


import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    boolean create(Role role);

    boolean delete(List<String> codes);

    boolean update(Role role);

    Role findByCode(String code);

    Role findByName(String name);

    PageInfo<Role> list(Integer page, Integer size, Map map);

    List<Role> findAll();

    List<Role> findByUserCode(String code);

    List<Role> findByDeptCode(String code);

}
