package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.Authority;
import io.terminus.common.model.Paging;

import java.util.List;

public interface AuthorityService {
    Authority getById(Long id);
    boolean update(Authority authority);
    boolean add(Authority authority);
    boolean del(Long id);
    Authority getByName(String name);
    List<Authority> getAll( );
    Paging findpage(int pageSize, int pageNum,Authority authority);
    List<String> findName();
}
