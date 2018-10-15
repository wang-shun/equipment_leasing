package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElProducePlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElProducePlanMapper {
    int delete(Long id);

    int create(ElProducePlan record);

    ElProducePlan findById(Long id);

    int update(ElProducePlan record);

    List<ElProducePlan> list(ElProducePlan elProducePlan);
}