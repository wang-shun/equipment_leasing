package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Authority;

import java.util.List;
import java.util.Map;

public interface AuthorityService {

    boolean create(Authority authority);

    boolean delete(List<String> codes);

    boolean update(Authority authority);

    PageInfo<Authority> findByPage(Integer page, Integer size, Map map);

    Authority findByNameAndPcode(Map map);

    List<Authority> findAll();

    Authority findByCode(String code);

    List<Authority> findByUserCode(String code);

}
