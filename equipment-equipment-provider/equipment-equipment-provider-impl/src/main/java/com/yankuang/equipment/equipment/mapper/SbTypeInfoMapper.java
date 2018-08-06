package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbTypeInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SbTypeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SbTypeInfo record);

    int insertSelective(SbTypeInfo record);

    SbTypeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SbTypeInfo record);

    int updateByPrimaryKey(SbTypeInfo record);
}