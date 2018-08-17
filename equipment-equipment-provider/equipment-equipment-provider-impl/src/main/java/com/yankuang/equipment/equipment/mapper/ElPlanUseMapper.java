package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanUse;
import org.springframework.stereotype.Repository;

@Repository
public interface ElPlanUseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ElPlanUse record);

    int insertSelective(ElPlanUse record);

    ElPlanUse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ElPlanUse record);

    int updateByPrimaryKey(ElPlanUse record);
}