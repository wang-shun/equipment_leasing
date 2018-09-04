package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    Boolean create(User user);

    Boolean delete(List<String> codes);

    Boolean update(User user);

    User findByName(String name);

    User findByAccount(String account);

    User findByCode(String code);

    List<User> list(Map map);

    Boolean stop(String code);

    Boolean start(String code);

}
