package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.DeptUser;

import java.util.List;
import java.util.Map;

public interface DeptUserService {

    Boolean create(DeptUser deptUser);

    Boolean deleteByUserCode(List<String> codes);

    Boolean deleteByDeptCode(List<String> codes);

    List<DeptUser> findByUserCode(List<String> codes);

    List<DeptUser> findByDeptCode(List<String> codes);

    List<DeptUser> findByDeptCodeAndUserCode(Map map);

}
