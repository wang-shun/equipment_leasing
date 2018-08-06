package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrgsDeptMappingMapper;
import com.yankuang.equipment.authority.model.OrgsDeptMapping;
import com.yankuang.equipment.authority.service.OrgsDeptMappingService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class OrgsDeptMappingServiceImpl implements OrgsDeptMappingService{

    @Autowired
    OrgsDeptMappingMapper orgsDeptMappingMapper;

    public boolean add(OrgsDeptMapping orgsDeptMapping){
        orgsDeptMapping.setCreate_by("小狼");//TODO 由于用户功能暂未开发完，先写死，后期改
        orgsDeptMapping.setUpdate_by("小狼");
        return orgsDeptMappingMapper.create(orgsDeptMapping);
    }
}
