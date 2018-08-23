package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface RoleMapper {

    List<Role> list(Map role);

    Role findByName(String name);

    List<Role> findAll();

    Role findById(Long id);

    boolean update(Role role);

    boolean create(Role role);

    boolean delete(Long id);

}
