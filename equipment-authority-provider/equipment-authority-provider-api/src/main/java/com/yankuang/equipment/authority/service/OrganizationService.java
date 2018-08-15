package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Organization;
import io.terminus.common.model.Paging;

import java.util.List;
import java.util.Map;

public interface OrganizationService {

    Organization findById(Long id);

    boolean create(Organization organization);

    boolean update(Organization organization);

    boolean delete(Long id);

    Organization findByName(String name);

    List<Organization> findAll();

    PageInfo<Organization> findByPage(int page, int size, Map orgMap);

}
