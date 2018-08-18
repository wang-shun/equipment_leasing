package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbEquipmentT;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbEquipmentTMapper {
    int deleteById(Long id);

    int create(SbEquipmentT record);

    SbEquipmentT findById(Long id);

    SbEquipmentT findByCode(String code);

    int update(SbEquipmentT record);

    List<SbEquipmentT> list(SbEquipmentT sbEquipmentT);

    int deletes(@Param("ids") List<Long> ids);
}