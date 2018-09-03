package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptUserMapper {

    Boolean create(DeptUser deptUser);

    Boolean deleteByUserCode(String code);

    Boolean deleteByDeptCode(String code);

    DeptUser findByUserCode(String code);

    List<DeptUser> findByDeptCode(String code);

    List<DeptUser> findByDeptCodeAndUserCode(Map map);

}
