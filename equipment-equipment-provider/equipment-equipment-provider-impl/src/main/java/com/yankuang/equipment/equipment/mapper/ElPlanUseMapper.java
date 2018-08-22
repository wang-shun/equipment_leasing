package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanUse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ElPlanUseMapper {
    int delete(Long id);

    int insert(ElPlanUse elPlanUse);

    ElPlanUse findById(Long id);

    int update(ElPlanUse record);

    List<ElPlanUse> list(Map elPlanUseMap);

    List<ElPlanUse> findByCondition(ElPlanUse elPlanUse);
}