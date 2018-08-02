package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrganizationMapper;
import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RpcProvider
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationMapper organizationMapper;

    public Organization getById(Long id) {
        return organizationMapper.findById(id);
    }
}
