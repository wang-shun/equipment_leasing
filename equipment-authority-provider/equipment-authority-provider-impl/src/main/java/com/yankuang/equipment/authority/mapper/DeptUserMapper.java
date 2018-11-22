package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptUserMapper {

    Boolean create(DeptUser deptUser);

    Boolean deleteByUserCode(List<String> codes);

    Boolean deleteByDeptCode(List<String> codes);

    List<DeptUser> findByUserCode(List<String> codes);

    List<DeptUser> findByDeptCode(List<String> codes);

    List<DeptUser> findByDeptCodeAndUserCode(Map map);

}
