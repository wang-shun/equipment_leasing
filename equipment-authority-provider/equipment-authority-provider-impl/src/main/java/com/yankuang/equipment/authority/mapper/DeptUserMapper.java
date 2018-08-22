package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptUserMapper {

    Boolean create(DeptUser userId);

    Boolean deleteByUserId(Long id);

    Boolean deleteByDeptId(Long id);

    DeptUser findByUserId(Long id);

    List<DeptUser> findByDeptId(Long id);

    List<DeptUser> findByDeptIdAndUserId(Map map);

}
