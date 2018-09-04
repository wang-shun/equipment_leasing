package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleUser;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleUserMapper {

    Boolean create(RoleUser roleUser);

    Boolean deleteByUserCode(String code);

    Boolean deleteByRoleCode(String code);

    List<RoleUser> findByUserCode(String code);

    List<RoleUser> findByRoleCode(String code);

}
