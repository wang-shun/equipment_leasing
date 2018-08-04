package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.Organization;

import java.util.List;

public interface OrganizationService {

    Organization getById(Long id);
    boolean add(Organization organization);
    boolean update(Organization organization);
    boolean del(Long id);
    Organization getByName(String name);
    List<Organization> getAll( );
}
