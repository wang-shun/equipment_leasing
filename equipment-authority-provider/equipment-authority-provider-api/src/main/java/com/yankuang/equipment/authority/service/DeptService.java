package com.yankuang.equipment.authority.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Dept;

import java.util.List;
import java.util.Map;

public interface DeptService {

    Boolean create(Dept user);

    Boolean delete(List<String> codes);

    Boolean update(Dept user);

    Dept findByCode(String code);

    Dept findByName(String name);

    List<Dept> findAll();

    PageInfo<Dept> findByPage(Integer page, Integer size, Map dept);

}
