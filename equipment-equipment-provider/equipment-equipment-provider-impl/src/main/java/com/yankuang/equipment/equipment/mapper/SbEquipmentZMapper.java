package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbEquipmentZ;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbEquipmentZMapper {
    int deleteById(Long id);

    int create(SbEquipmentZ record);

    SbEquipmentZ findById(Long id);

    int update(SbEquipmentZ record);

    List<SbEquipmentZ> list(@Param("p_code") String code, @Param("p_name") String name);
}