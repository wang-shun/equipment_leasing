package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Dept;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMapper {

    Boolean create(Dept user);

    Boolean delete(Long id);

    Boolean update(Dept user);

    Dept findById(Long id);

    Dept findByName(String name);

    List<Dept> findAll();

    List<Map> list(Map map);

}
