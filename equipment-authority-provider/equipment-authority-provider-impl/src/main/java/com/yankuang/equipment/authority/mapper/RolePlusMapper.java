package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RolePlusMapper {

    List<Role> list(Map role);

    Role findByName(String name);

    List<Role> getAll( );

    Role findRoles(Long roleId);
}
