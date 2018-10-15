package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElEquipmentGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElEquipmentGroupMapper {
    int delete(Long id);

    int create(ElEquipmentGroup record);

    ElEquipmentGroup findById(Long id);

    int update(ElEquipmentGroup record);

    List<ElEquipmentGroup> findBySurfaceCode(String surfaceCode);

    List<ElEquipmentGroup> findByCondition(ElEquipmentGroup groupI);
}