package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrgDeptMapper;
import com.yankuang.equipment.authority.model.OrgDept;
import com.yankuang.equipment.authority.service.OrgDeptService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class OrgDeptServiceImpl implements OrgDeptService {
    @Autowired
    OrgDeptMapper orgDeptMapper;

    public boolean add(OrgDept orgDept){
        return orgDeptMapper.create(orgDept);
    }

    public boolean delById(Long id){
        return orgDeptMapper.updatedel(id);
    }

    public boolean update(OrgDept orgDept){
        return orgDeptMapper.update(orgDept);
    }

    public OrgDept selOrgDept(Long id){
        return orgDeptMapper.findById(id);
    }

    public Long findOrgDept(OrgDept orgDept){
        return orgDeptMapper.findOrgDept(orgDept);
    }
    public Boolean udtOrgDept(OrgDept orgDept){
        return orgDeptMapper.update(orgDept);
    }

    public Paging findpage(int pageSize, int pageNum, OrgDept orgDept){
        int maxResult = (pageNum - 1) * pageSize;
        Paging page = orgDeptMapper.paging(maxResult, pageNum, orgDept);
        return page;
    }
    public List<Long> findDeptId(Long organizationId){
        return orgDeptMapper.findDeptId(organizationId);
    }
}
