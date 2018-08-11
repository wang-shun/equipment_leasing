package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleAuthority;

import java.util.List;
import java.util.Map;

public interface RoleAuthorityService {

    boolean create(RoleAuthority roleAuthority);

    List<RoleAuthority> findByRoleId(Long roleId);

    RoleAuthority findByRoleIdAndAuthorityId(Map map);

}
