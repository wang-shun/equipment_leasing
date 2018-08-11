package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.Authority;
import io.terminus.common.model.Paging;

import java.util.List;

public interface AuthorityService {
    Authority getById(Long id);
    boolean update(Authority authority);
    boolean add(Authority authority);
    boolean delete(Long id);
    Authority findByName(String name);
    List<Authority> findAll();
    Paging paging(Integer page, Integer size,Authority authority);
}
