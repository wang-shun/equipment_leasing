package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.Dept;
import io.terminus.common.model.Paging;

import java.util.List;

public interface DeptService {
    Dept getById(Long id);

    boolean update(Dept dept);
    boolean add(Dept dept);
    boolean del(Long id);

    Dept getByName(String name);
    List<Dept> getAll( );
    Paging findpage(int pageSize, int pageNum, Dept dept);
    List<String> findName();
    Long getId(String name);
    Dept findDept(Long deptId);
}
