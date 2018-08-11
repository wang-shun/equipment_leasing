package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbPosition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbPositionMapper {
    int deleteById(Long id);

    int create(SbPosition record);

    SbPosition findById(Long id);

    int update(SbPosition record);

    List<SbPosition> list(String code,String name);

    SbPosition selectByMaxId();
}