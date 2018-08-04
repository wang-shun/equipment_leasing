package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.RoleUser;

import java.util.List;

public interface RoleUserService {

    boolean create(RoleUser roleAuthority);

    List<RoleUser> findByUserId(Long userId);
}
