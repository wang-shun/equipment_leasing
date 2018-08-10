package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.AuthorityGroupMapping;

import java.util.List;
import java.util.Map;

public interface AuthorityGroupMappingService {

    AuthorityGroupMapping findById(Long id);

    List<AuthorityGroupMapping> findByGroupId(Long id);

    Boolean create(AuthorityGroupMapping t);

    AuthorityGroupMapping selectByAuthorityIdAndGroupId(Map map);
}
