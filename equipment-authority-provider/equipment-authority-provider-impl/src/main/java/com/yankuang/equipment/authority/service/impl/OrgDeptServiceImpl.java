package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrgDeptMapper;
import com.yankuang.equipment.authority.model.OrgDept;
import com.yankuang.equipment.authority.service.OrgDeptService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class OrgDeptServiceImpl implements OrgDeptService {
    @Autowired
    OrgDeptMapper orgDeptMapper;

    public boolean add(OrgDept orgDept){
        orgDept.setCreateBy("小狼");//TODO 由于用户功能暂未开发完，先写死，后期改
        orgDept.setUpdateBy("小狼");
        return orgDeptMapper.create(orgDept);
    }

    public boolean delById(Long id){
        return orgDeptMapper.updatedel(id);
    }

    public boolean update(OrgDept orgDept){
        return orgDeptMapper.update(orgDept);
    }

}
