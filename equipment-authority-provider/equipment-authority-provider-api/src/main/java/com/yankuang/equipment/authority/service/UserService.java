package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.User;

public interface UserService {

    User login(String name);

    public User findByCode(String code);

    boolean create(User user);

    String healthCheck();
}
