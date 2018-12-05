package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleAuthority;

import java.util.List;
import java.util.Map;

public interface RoleAuthorityService {

    Boolean create(RoleAuthority t);

    RoleAuthority findByRoleAndAuthorityCodes(Map map);

    boolean deleteByAuthorityCodes(List<String> codes);

    boolean deleteByRoleCodes(List<String> codes);

}
