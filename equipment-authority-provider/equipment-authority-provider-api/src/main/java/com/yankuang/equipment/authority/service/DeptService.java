package com.yankuang.equipment.authority.service;

import com.yankuang.equipment.authority.model.Dept;

import java.util.List;

public interface DeptService {
    Dept getById(Long id);

    boolean update(Dept dept);
    boolean add(Dept dept);
    boolean del(Long id);

    Dept getByName(String name);
    List<Dept> getAll( );
}
