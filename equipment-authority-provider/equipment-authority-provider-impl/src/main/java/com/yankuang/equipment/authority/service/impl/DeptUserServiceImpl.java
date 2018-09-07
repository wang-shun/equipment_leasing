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

    public Boolean deleteByUserCode(List<String> codes) {
        return deptUserMapper.deleteByUserCode(codes);
    }

    public Boolean deleteByDeptCode(List<String> codes) {
        return deptUserMapper.deleteByDeptCode(codes);
    }

    public List<DeptUser> findByUserCode(List<String> codes) {
        return deptUserMapper.findByUserCode(codes);
    }

    public List<DeptUser> findByDeptCode(List<String> codes) {
        return deptUserMapper.findByDeptCode(codes);
    }

    public List<DeptUser> findByDeptCodeAndUserCode(Map map) {
        return deptUserMapper.findByDeptCodeAndUserCode(map);
    }

}
