package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptUser;

public interface DeptUserService {

    Long findDeptId(Long userId);

    Boolean create(DeptUser deptUser);

    Boolean deleteByUserId(Long userId);

    Boolean update(DeptUser deptUser);
}
