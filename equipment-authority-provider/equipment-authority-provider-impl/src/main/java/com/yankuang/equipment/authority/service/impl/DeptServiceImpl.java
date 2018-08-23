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

    public Boolean delete(Long id) {
        return deptMapper.delete(id);
    }

    public Boolean update(Dept dept) {
        return deptMapper.update(dept);
    }

    public Dept findById(Long id) {
        return deptMapper.findById(id);
    }

    public Dept findByName(String name) {
        return deptMapper.findByName(name);
    }

    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    public PageInfo<Map> findByPage(Integer page, Integer size, Map map) {
        PageHelper.startPage(page, size);
        List<Map> maps = deptMapper.list(map);
        PageInfo<Map> pageInfo = new PageInfo<Map>(maps);
        return pageInfo;
    }
}
