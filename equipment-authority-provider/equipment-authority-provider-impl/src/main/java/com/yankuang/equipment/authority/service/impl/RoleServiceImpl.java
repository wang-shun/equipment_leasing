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

@Service
@RpcProvider
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    public boolean create(Role role) {
        System.out.println(role.getName());
        return roleMapper.create(role);
    }

    public boolean delete(List<String> codes) {
        return roleMapper.delete(codes);
    }

    public boolean update(Role role) {
        return roleMapper.update(role);
    }

    public Role findByCode(String code) {
        return roleMapper.findByCode(code);
    }

    public Role findByName(String name) {
        return roleMapper.findByName(name);
    }

    public PageInfo<Role> list(Integer page, Integer size, Role role) {
        PageHelper.startPage(page, size);
        List<Role> roles = roleMapper.list(role);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
        return pageInfo;
    }

    public List<Role> findAll() {
        return roleMapper.list(null);
    }

    public List<Role> findByUserCode(String code) {
        return roleMapper.findByUserCode(code);
    }

    public List<Role> findByDeptCode(String code) {
        return roleMapper.findByDeptCode(code);
    }

}
