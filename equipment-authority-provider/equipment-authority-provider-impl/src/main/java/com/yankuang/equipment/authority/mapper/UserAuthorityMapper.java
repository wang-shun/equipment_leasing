package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.UserAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserAuthorityMapper {

    Boolean create(UserAuthority t);

    Boolean deleteByAuthorityCodes(List<String> codes);

    Boolean deleteByUserCodes(List<String> codes);

    List<UserAuthority> findByUserCodes(List<String> codes);

    List<UserAuthority> findByAuthorityCodes(List<String> codes);

    UserAuthority findByUserAndAuthorityCodes(Map map);

}
