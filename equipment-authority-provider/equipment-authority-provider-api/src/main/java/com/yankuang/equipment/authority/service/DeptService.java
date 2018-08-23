package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Dept;

import java.util.List;
import java.util.Map;

public interface DeptService {

    Boolean create(Dept dept);

    Boolean delete(Long id);

    Boolean update(Dept dept);

    Dept findById(Long id);

    Dept findByName(String name);

    List<Dept> findAll();

    PageInfo<Map> findByPage(Integer page, Integer size, Map dept);

}
