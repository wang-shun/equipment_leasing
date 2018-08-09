package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.AuthorityGroup;

public interface AuthorityGroupService {

    AuthorityGroup findByName(String name);

    Boolean create(AuthorityGroup authorityGroup);
}
