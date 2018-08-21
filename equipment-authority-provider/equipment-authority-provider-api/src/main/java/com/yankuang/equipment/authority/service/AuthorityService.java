package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Authority;
import io.terminus.common.model.Paging;

import java.util.List;
import java.util.Map;

public interface AuthorityService {

    PageInfo<Authority> findByPage(Integer page, Integer size, Map map);

    Authority findByName(String name);

    List<Authority> findAll();

    Authority findById(Long id);

    boolean update(Authority authority);

    boolean create(Authority authority);

    boolean delete(Long id);
}
