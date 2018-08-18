package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanUse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElPlanUseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ElPlanUse elPlanUse);

    ElPlanUse selectById(Long id);

    int update(ElPlanUse record);

    List<ElPlanUse> findByCondition(ElPlanUse elPlanUse);
}