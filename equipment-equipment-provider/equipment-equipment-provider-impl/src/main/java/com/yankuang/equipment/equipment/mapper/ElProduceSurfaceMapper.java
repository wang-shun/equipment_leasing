package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElProduceSurface;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElProduceSurfaceMapper {
    int delete(Long id);

    int create(ElProduceSurface record);

    ElProduceSurface findById(Long id);

    int update(ElProduceSurface record);

    List<ElProduceSurface> findByPlanCode(String planCode);

    List<ElProduceSurface> findByCondition(ElProduceSurface surfaceI);
}