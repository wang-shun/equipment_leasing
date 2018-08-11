package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.AuthorityGroupMapping;

import java.util.Map;

public interface AuthorityGroupMappingService {

    AuthorityGroupMapping findById(Long id);

    Boolean create(AuthorityGroupMapping t);

    AuthorityGroupMapping selectByAuthorityIdAndGroupId(Map map);
}
