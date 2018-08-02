package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.AuthorityMapper;
import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class AuthorityServiceImpl implements AuthorityService{
    @Autowired
    AuthorityMapper authorityMapper;

    public Authority getById(Long id) {
        return authorityMapper.findById(id);
    }
}
