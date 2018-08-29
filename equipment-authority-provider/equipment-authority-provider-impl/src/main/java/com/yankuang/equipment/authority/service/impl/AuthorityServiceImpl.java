package com.yankuang.equipment.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.mapper.AuthorityMapper;
import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
@Transactional
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    AuthorityMapper authorityMapper;

    public Authority findById(Long id) {
        return authorityMapper.findById(id);
    }

    public List<Authority> findByUserId(Long userId) {
        return authorityMapper.findByUserId(userId);
    }

    public boolean update(Authority authority){
        return authorityMapper.update(authority);
    }

    public boolean create(Authority authority){
        return authorityMapper.create(authority);
    }

    public boolean delete(Long id){
        return  authorityMapper.delete(id);
    }

    public boolean deletes(List<Long> ids){
        return  authorityMapper.deletes(ids);
    }

    public Authority findByName(String name){
        return authorityMapper.findByName(name);
    }

    public List<Authority> findAll( ){
        return authorityMapper.findAll();
    }

    public PageInfo<Authority> findByPage(Integer page, Integer size, Map authority) {
        PageHelper.startPage(page, size);
        List<Authority> authorities = authorityMapper.list(authority);
        PageInfo<Authority> pageInfo = new PageInfo<Authority>(authorities);
        return pageInfo;
    }

}
