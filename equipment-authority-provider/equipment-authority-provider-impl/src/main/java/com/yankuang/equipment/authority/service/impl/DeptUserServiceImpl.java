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

    public Boolean deleteByUserCode(String code) {
        return deptUserMapper.deleteByUserCode(code);
    }

    public Boolean deleteByDeptCode(String code) {
        return deptUserMapper.deleteByDeptCode(code);
    }

    public DeptUser findByUserCode(String code) {
        return deptUserMapper.findByUserCode(code);
    }

    public List<DeptUser> findByDeptCode(String code) {
        return deptUserMapper.findByDeptCode(code);
    }

    public List<DeptUser> findByDeptCodeAndUserCode(Map map) {
        return deptUserMapper.findByDeptCodeAndUserCode(map);
    }


}
