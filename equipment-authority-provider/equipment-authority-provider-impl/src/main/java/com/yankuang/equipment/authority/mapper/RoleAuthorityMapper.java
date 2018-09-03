package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleAuthorityMapper {

    Boolean create(RoleAuthority t);

    List<RoleAuthority> findByRoleCode(String code);

    RoleAuthority findByRoleAndAuthorityCodes(Map map);

    boolean delete(List<String> codes);
}
