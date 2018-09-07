package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.UserAuthority;

import java.util.List;
import java.util.Map;

public interface UserAuthorityService {

    Boolean create(UserAuthority t);

    Boolean deleteByAuthorityCodes(List<String> codes);

    Boolean deleteByUserCodes(List<String> codes);

    List<UserAuthority> findByUserCodes(List<String> codes);

    List<UserAuthority> findByAuthorityCodes(List<String> codes);

    UserAuthority findByUserAndAuthorityCodes(Map map);

}
