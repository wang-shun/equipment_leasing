package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.AuthorityMapper;
import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class AuthorityServiceImpl implements AuthorityService{
    @Autowired
    AuthorityMapper authorityMapper;

    public Authority getById(Long id) {
        return authorityMapper.findById(id);
    }

    public boolean update(Authority authority){
        return authorityMapper.update(authority);
    }

    public boolean add(Authority authority){
        return authorityMapper.create(authority);
    }

    public boolean del(Long id){
        return  authorityMapper.updatedel(id);
    }

    public Authority getByName(String name){
        return authorityMapper.findByName(name);
    }

    public List<Authority> getAll( ){
        return authorityMapper.getAll();
    }

    public Paging findpage(int pageSize, int pageNum, Authority authority) {
            int maxResult = (pageNum - 1) * pageSize;
            Paging page = authorityMapper.paging(maxResult, pageNum, authority);
            return page;
    }
    public List<String> findName(){
        return authorityMapper.getName();
    }
}
