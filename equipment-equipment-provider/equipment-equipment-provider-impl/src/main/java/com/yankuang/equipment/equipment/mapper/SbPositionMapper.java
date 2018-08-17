package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbPositionMapper {
    int deleteById(Long id);

    int create(SbPosition record);

    SbPosition findById(Long id);

    List<SbPosition> findByPosition(String position);

    int update(SbPosition record);

    List<SbPosition> list(@Param("p_code") String code, @Param("p_name") String name);

    SbPosition selectByMaxId();
}