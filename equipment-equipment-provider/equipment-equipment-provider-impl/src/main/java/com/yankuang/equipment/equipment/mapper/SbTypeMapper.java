package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SbType record);

    int insertSelective(SbType record);

    SbType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SbType record);

    int updateByPrimaryKey(SbType record);

    List<SbType> selectAllSbTypes();
}