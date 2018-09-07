package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface RoleUserMapper {

    Boolean create(RoleUser roleUser);

    Boolean deleteByUserCode(List<String> codes);

    Boolean deleteByRoleCode(List<String> codes);

    List<RoleUser> findByUserCode(List<String> codes);

    List<RoleUser> findByRoleCode(List<String> codes);

    List<RoleUser> findByRoleCodeAndUserCode(Map map);

}
