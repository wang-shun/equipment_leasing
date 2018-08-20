package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanUse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElPlanUseMapper {
    int deleteById(Long id);

    int insert(ElPlanUse elPlanUse);

    ElPlanUse selectByPrimaryKey(Long id);

    int update(ElPlanUse record);

    List<ElPlanUse> findByCondition(ElPlanUse elPlanUse);
}