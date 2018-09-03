package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptUser;

import java.util.List;
import java.util.Map;

public interface DeptUserService {

    Boolean create(DeptUser deptUser);

    Boolean deleteByUserCode(String code);

    Boolean deleteByDeptCode(String code);

    DeptUser findByUserCode(String code);

    List<DeptUser> findByDeptCode(String code);

    List<DeptUser> findByDeptCodeAndUserCode(Map map);

}
