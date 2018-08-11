package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbModelMapper {
    int deleteById(Long id);

    int create(SbModel record);

    SbModel findById(Long id);

    int update(SbModel record);

    List<SbModel> list(String code,String name);

    SbModel selectByMaxId();
}