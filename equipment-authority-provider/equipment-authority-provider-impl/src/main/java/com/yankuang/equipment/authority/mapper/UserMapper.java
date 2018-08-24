package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    Boolean create(User user);

    Boolean delete(Long id);

    Boolean update(User user);

    User findByName(String name);

    User findByAccount(String account);

    User findById(Long account);

    List<Map> list(Map map);

    List<User> findAll();

    Boolean stop(Long id);

    Boolean start(Long id);

}
