package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElProducePlan;
import org.springframework.stereotype.Repository;

@Repository
public interface ElProducePlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ElProducePlan record);

    int insertSelective(ElProducePlan record);

    ElProducePlan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ElProducePlan record);

    int updateByPrimaryKey(ElProducePlan record);
}