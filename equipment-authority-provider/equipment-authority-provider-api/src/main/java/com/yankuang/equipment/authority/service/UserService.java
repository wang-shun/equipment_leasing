package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.User;
import io.terminus.common.model.Paging;

public interface UserService {

    User login(String name);

    User findByCode(String code);

    User findById(Long id);

    boolean create(User user);

    String healthCheck();

    Paging<User> paging(Integer page, Integer size, User user);

    Boolean delete(Long id);

    Boolean update(User t);
}
