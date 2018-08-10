package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbPosition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbPositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SbPosition record);

    int insertSelective(SbPosition record);

    SbPosition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SbPosition record);

    int updateByPrimaryKey(SbPosition record);

    List<SbPosition> listAll(String code,String name);

    SbPosition selectByMaxId();
}