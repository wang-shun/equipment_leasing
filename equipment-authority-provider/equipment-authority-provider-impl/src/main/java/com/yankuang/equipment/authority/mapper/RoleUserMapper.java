package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleUser;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleUserMapper {

    Boolean create(RoleUser roleUser);

    Boolean deleteByUserId(Long id);

    Boolean deleteByRoleId(Long id);

    List<RoleUser> findByUserId(Long id);

    List<RoleUser> findByRoleId(Long id);

}
