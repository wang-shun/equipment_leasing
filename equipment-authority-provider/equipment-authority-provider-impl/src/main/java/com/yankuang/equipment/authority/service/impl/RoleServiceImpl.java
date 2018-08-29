package com.yankuang.equipment.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.mapper.RoleMapper;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RoleService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> findByUserId(Long id) {
        return roleMapper.findByUserId(id);
    }

    public Role findById(Long id) {
        return roleMapper.findById(id);
    }

    public boolean update(Role role) {
        return roleMapper.update(role);
    }

    public boolean create(Role role) {
        return roleMapper.create(role);
    }

    public boolean delete(Long id) {
        return roleMapper.delete(id);
    }

    public Role findByName(String name) {
        return roleMapper.findByName(name);
    }

    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    public PageInfo<Role> list(Integer page, Integer size, Map roleMap) {
        PageHelper.startPage(page, size);
        List<Role> roles = roleMapper.list(roleMap);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
        return pageInfo;
    }

}
