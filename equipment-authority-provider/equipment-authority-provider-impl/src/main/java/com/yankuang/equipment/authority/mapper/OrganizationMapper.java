package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrganizationMapper {

    Organization findById(Long id);

    boolean create(Organization organization);

    boolean update(Organization organization);

    List<Organization> findAll();

    List<Organization> list(Map map);

    Boolean delete(Long id);

    Organization findByName(String name);

    List<Organization> findByPId( );
}