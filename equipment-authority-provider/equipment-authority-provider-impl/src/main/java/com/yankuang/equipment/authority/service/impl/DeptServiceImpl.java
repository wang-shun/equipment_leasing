package com.yankuang.equipment.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.mapper.DeptMapper;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class DeptServiceImpl implements DeptService {

    @Autowired
    DeptMapper deptMapper;

    public Boolean create(Dept dept) {
        return deptMapper.create(dept);
    }

    public Boolean delete(List<String> codes) {
        return deptMapper.delete(codes);
    }

    public Boolean update(Dept user) {
        return deptMapper.update(user);
    }

    public Dept findByCode(String code) {
        return deptMapper.findByCode(code);
    }

    public Dept findByNameAndPcode(Map map) {
        return deptMapper.findByNameAndPcode(map);
    }

    public List<Dept> findAll() {
        return deptMapper.list(null);
    }

    public PageInfo<Dept> findByPage(Integer page, Integer size, Map map) {
        PageHelper.startPage(page, size);
        List<Dept> maps = deptMapper.list(map);
        PageInfo<Dept> pageInfo = new PageInfo<Dept>(maps);
        return pageInfo;
    }

}
