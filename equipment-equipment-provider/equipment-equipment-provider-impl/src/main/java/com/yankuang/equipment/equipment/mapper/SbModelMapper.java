package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbModel;
import org.springframework.stereotype.Repository;

@Repository
public interface SbModelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SbModel record);

    int insertSelective(SbModel record);

    SbModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SbModel record);

    int updateByPrimaryKey(SbModel record);
}