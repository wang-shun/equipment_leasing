package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElEquipmentGroupConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElEquipmentGroupConfigMapper {
    int delete(Long id);

    int create(ElEquipmentGroupConfig record);

    int createBatch(List<ElEquipmentGroupConfig> record);

    ElEquipmentGroupConfig findById(Long id);

    int update(ElEquipmentGroupConfig record);

    List<ElEquipmentGroupConfig> list(ElEquipmentGroupConfig elEquipmentGroupConfig);
}