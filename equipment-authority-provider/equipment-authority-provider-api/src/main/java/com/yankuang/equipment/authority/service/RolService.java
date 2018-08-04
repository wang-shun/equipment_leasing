package com.yankuang.equipment.authority.service;


import com.yankuang.equipment.authority.model.Role;
import io.terminus.common.model.Paging;

import java.util.List;

public interface RolService {
    Role getById(Long id);
    boolean update(Role role);
    boolean add(Role role);
    boolean del(Long id);
    List<Role> getAll(List<Long> ids);
    Role getByName(String name);
    List<Role> getAll( );
    Paging findpage(int pageSize, int pageNum, Role role);
}
