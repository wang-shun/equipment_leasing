package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Dept;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMapper {

    Boolean create(Dept user);

    Boolean delete(List<String> codes);

    Boolean update(Dept user);

    Dept findByCode(String code);

    Dept findByNameAndPcode(Map map);

    List<Dept> list(Map map);

}
