package com.yankuang.equipment.authority.service;


import com.yankuang.equipment.authority.model.Role;
import io.terminus.common.model.Paging;

import java.util.List;

public interface RoleService {
    Role getById(Long id);
    boolean update(Role role);
    boolean create(Role role);
    boolean delete(Long id);
    Role findByName(String name);
    List<Role> getAll( );
    Role findRoles(Long roleId);
    Paging paging(Integer page, Integer size, Role role);

}
