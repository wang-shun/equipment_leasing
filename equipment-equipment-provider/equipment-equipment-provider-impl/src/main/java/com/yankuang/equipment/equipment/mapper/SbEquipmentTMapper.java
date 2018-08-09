package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbEquipmentT;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbEquipmentTMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SbEquipmentT record);

    int insertSelective(SbEquipmentT record);

    SbEquipmentT selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SbEquipmentT record);

    int updateByPrimaryKey(SbEquipmentT record);

    List<SbEquipmentT> listAll(SbEquipmentT sbEquipmentT);
}