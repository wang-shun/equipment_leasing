package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElProduceSurface;
import org.springframework.stereotype.Repository;

@Repository
public interface ElProduceSurfaceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ElProduceSurface record);

    int insertSelective(ElProduceSurface record);

    ElProduceSurface selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ElProduceSurface record);

    int updateByPrimaryKey(ElProduceSurface record);
}