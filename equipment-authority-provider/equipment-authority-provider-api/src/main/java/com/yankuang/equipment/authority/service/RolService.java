package com.yankuang.equipment.authority.service;


import com.yankuang.equipment.authority.model.Role;

import java.util.List;

public interface RolService {
    Role getById(Long id);
    boolean update(Role role);
    boolean add(Role role);
    boolean del(Long id);
    List<Role> getAll(List<Long> ids);
    Role getByName(String name);
    List<Role> getAll( );
}
