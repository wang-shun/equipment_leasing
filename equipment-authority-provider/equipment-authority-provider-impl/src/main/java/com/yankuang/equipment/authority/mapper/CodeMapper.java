package com.yankuang.equipment.authority.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CodeMapper {

    Long findIdMax(Map map);

}
