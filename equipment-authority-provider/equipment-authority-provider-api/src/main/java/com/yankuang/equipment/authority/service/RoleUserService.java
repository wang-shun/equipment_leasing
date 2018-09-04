package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleUser;

import java.util.List;

public interface RoleUserService {

    Boolean create(RoleUser roleUser);

    Boolean deleteByUserCode(String code);

    Boolean deleteByRoleCode(String code);

    List<RoleUser> findByUserCode(String code);

    List<RoleUser> findByRoleCode(String code);

}
