package com.yankuang.equipment.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.mapper.OrganizationMapper;
import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RpcProvider
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationMapper organizationMapper;

    public Organization findById(Long id) {
        return organizationMapper.findById(id);
    }

    public boolean create(Organization organization) {
        return organizationMapper.create(organization);
    }

    public boolean update(Organization organization) {
        return organizationMapper.update(organization);
    }

    public boolean delete(Long id) {
        return organizationMapper.delete(id);
    }

    public Organization findByName(String name) {
        return organizationMapper.findByName(name);
    }

    public List<Organization> findAll() {
        return organizationMapper.findAll();
    }

    public PageInfo<Organization> findByPage(int page, int size, Map orgMap) {
        PageHelper.startPage(page, size);
        List<Organization> organizations = organizationMapper.list(orgMap);
        PageInfo<Organization> pageInfo = new PageInfo<Organization>(organizations);
        return pageInfo;
    }

    public List<Organization> findByPId( ){
        return organizationMapper.findByPId( );
    }

}
