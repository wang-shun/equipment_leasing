package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptUser;

import java.util.List;
import java.util.Map;

public interface DeptUserService {

    Boolean create(DeptUser deptUser);

    Boolean deleteByUserId(Long id);

    Boolean deleteByDeptId(Long id);

    DeptUser findByUserId(Long id);

    List<DeptUser> findByDeptId(Long id);

    List<DeptUser> findByDeptIdAndUserId(Map map);

}
