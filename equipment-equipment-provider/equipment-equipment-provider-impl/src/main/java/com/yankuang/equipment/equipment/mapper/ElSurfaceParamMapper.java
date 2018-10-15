package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElSurfaceParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElSurfaceParamMapper {
    int delete(Long id);

    int create(ElSurfaceParam record);

    ElSurfaceParam findById(Long id);

    int update(ElSurfaceParam record);

    ElSurfaceParam findByCode(String code);

    List<ElSurfaceParam> list(ElSurfaceParam elSurfaceParam);

    List<ElSurfaceParam> findByPositionCode(String sbPositionCode);
}