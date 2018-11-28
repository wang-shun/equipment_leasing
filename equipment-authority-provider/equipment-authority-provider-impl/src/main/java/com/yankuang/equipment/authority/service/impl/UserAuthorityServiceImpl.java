package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.UserAuthorityMapper;
import com.yankuang.equipment.authority.model.UserAuthority;
import com.yankuang.equipment.authority.service.UserAuthorityService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class UserAuthorityServiceImpl implements UserAuthorityService {

    @Autowired
    UserAuthorityMapper userAuthorityMapper;


    public Boolean create(UserAuthority t) {
        return userAuthorityMapper.create(t);
    }

    public Boolean deleteByAuthorityCodes(List<String> codes) {
        return userAuthorityMapper.deleteByAuthorityCodes(codes);
    }

    public Boolean deleteByUserCodes(List<String> codes) {
        return userAuthorityMapper.deleteByUserCodes(codes);
    }

    public List<UserAuthority> findByUserCodes(List<String> codes) {
        return userAuthorityMapper.findByUserCodes(codes);
    }

    public List<UserAuthority> findByAuthorityCodes(List<String> codes) {
        return userAuthorityMapper.findByAuthorityCodes(codes);
    }

    public UserAuthority findByUserAndAuthorityCodes(Map map) {
        return userAuthorityMapper.findByUserAndAuthorityCodes(map);
    }

}
