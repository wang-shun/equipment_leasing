package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleAuthority;

import java.util.List;

public interface RoleAuthorityService {

    boolean create(RoleAuthority roleAuthority);

    List<RoleAuthority> findByRoleId(Long roleId);

}
