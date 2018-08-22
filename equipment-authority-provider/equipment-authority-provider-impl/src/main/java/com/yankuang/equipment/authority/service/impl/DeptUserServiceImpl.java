package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.DeptUserMapper;
import com.yankuang.equipment.authority.model.DeptUser;
import com.yankuang.equipment.authority.service.DeptUserService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class DeptUserServiceImpl implements DeptUserService {

    @Autowired
    DeptUserMapper deptUserMapper;

    public Boolean create(DeptUser deptUser) {
        return deptUserMapper.create(deptUser);
    }

    public Boolean deleteByUserId(Long id) {
        return deptUserMapper.deleteByUserId(id);
    }

    public Boolean deleteByDeptId(Long id) {
        return deptUserMapper.deleteByDeptId(id);
    }

    public DeptUser findByUserId(Long id) {
        return deptUserMapper.findByUserId(id);
    }

    public List<DeptUser> findByDeptId(Long id) {
        return deptUserMapper.findByDeptId(id);
    }

    public List<DeptUser> findByDeptIdAndUserId(Map map) {
        return deptUserMapper.findByDeptIdAndUserId(map);
    }
}
