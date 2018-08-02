package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.User;

public interface UserService {

    User login(String userName, String password);

    User getById(Long id);

    boolean create(User user);

    String healthCheck();
}
