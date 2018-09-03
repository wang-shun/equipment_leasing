package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleAuthority;

import java.util.List;
import java.util.Map;

public interface RoleAuthorityService {

    Boolean create(RoleAuthority t);

    List<RoleAuthority> findByRoleCode(String code);

    RoleAuthority findByRoleAndAuthorityCodes(Map map);

    boolean delete(List<String> codes);

}
