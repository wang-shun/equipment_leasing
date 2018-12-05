package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleAuthorityMapper {

    Boolean create(RoleAuthority t);

    RoleAuthority findByRoleAndAuthorityCodes(Map map);

    boolean deleteByAuthorityCodes(List<String> codes);

    boolean deleteByRoleCodes(List<String> codes);

}
