package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbTypeInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SbTypeInfoMapper {
    int deleteById(Long id);

    int deleteByTypeId(Long typeId);

    int create(SbTypeInfo record);

    SbTypeInfo findById(Long id);

    int update(SbTypeInfo record);

    SbTypeInfo findMainParaBySbtypeThree(String sbtypeThree);
}