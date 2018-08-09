package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptUserMapper;
import com.yankuang.equipment.authority.model.DeptUser;
import com.yankuang.equipment.authority.service.DeptUserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class DeptUserServiceImpl implements DeptUserService {

    @Autowired
    DeptUserMapper deptUserMapper;

    public Long findDeptId(Long userId){
        return deptUserMapper.findDeptId(userId);
    }

    public Boolean create(DeptUser deptUser){
        return deptUserMapper.create(deptUser);
    }

    public Boolean deleteByUserId(Long userId){
        return deptUserMapper.deleteByUserId(userId);
    }
}
