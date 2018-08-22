package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleUser;

import java.util.List;

public interface RoleUserService {

    Boolean create(RoleUser roleUser);

    Boolean deleteByUserId(Long id);

    Boolean deleteByRoleId(Long id);

    List<RoleUser> findByUserId(Long id);

    List<RoleUser> findByRoleId(Long id);

}
