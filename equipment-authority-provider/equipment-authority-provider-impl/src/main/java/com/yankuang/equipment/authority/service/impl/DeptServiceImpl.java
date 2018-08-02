package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptMapper;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class DeptServiceImpl implements DeptService{
    @Autowired
    DeptMapper deptMapper;

    public Dept getById(Long id) {
        return deptMapper.findById(id);
    }
}
