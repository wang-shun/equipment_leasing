package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrganizationMapper;
import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RpcProvider
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationMapper organizationMapper;

    public Organization getById(Long id) {
        return organizationMapper.findById(id);
    }

    public boolean add(Organization organization){
        organization.setCode(UuidUtils.newUuid());
        organization.setCreateBy("小狼");//TODO 由于用户功能暂未开发完，先写死，后期改
        organization.setUpdateBy("小狼");
        return organizationMapper.create(organization);
    }

    public boolean update(Organization organization){
        return organizationMapper.update(organization);
    }

    public boolean del(Long id){
        return  organizationMapper.updatedel(id);
    }

    public Organization getByName(String name){
        return organizationMapper.findByName(name);
    }

    public List<Organization> getAll( ){
        return organizationMapper.getAll( );
    }

    public Paging findpage(int pageSize, int pageNum, Organization organization){
        int maxResult = (pageNum - 1) * pageSize;
        Paging page = organizationMapper.paging(maxResult, pageNum, organization);
        return page;
    }
}
