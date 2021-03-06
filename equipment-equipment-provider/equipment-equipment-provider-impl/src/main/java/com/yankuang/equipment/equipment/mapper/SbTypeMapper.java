package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbTypeMapper {
    int deleteById(Long id);

    int deleteByPcode(@Param("p_code") String p_code);

    int create(SbType record);

    SbType findById(Long id);

    SbType findByCode(String code);

    int update(SbTypeInfo record);

    List<SbType> list();

    List<SbType> listByPcodeOrLevel(@Param("p_code") String p_code, @Param("p_level") Integer level);
}