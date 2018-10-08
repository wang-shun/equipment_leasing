package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElEquipmentGroup;
import org.springframework.stereotype.Repository;

@Repository
public interface ElEquipmentGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ElEquipmentGroup record);

    int insertSelective(ElEquipmentGroup record);

    ElEquipmentGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ElEquipmentGroup record);

    int updateByPrimaryKey(ElEquipmentGroup record);
}