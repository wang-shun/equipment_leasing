package com.yankuang.equipment.equipment.mapper;


import com.yankuang.equipment.equipment.model.SbType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbTypeMapper {
    int deleteById(Long id);

    int create(SbType record);

    SbType findById(Long id);

    int update(SbType record);

    List<SbType> list();

    List<SbType> listByPcodeOrLevel(String pcode,int level);
}