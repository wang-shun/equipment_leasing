package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleUser;

import java.util.List;
import java.util.Map;

public interface RoleUserService {

    Boolean create(RoleUser roleUser);

    Boolean deleteByUserCode(List<String> codes);

    Boolean deleteByRoleCode(List<String> codes);

    List<RoleUser> findByUserCode(List<String> codes);

    List<RoleUser> findByRoleCode(List<String> codes);

    List<RoleUser> findByRoleCodeAndUserCode(Map map);

}
