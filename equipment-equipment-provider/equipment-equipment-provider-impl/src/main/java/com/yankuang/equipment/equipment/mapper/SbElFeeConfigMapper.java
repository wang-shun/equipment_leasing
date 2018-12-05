package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.SbElFeeConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbElFeeConfigMapper {

    int deleteById(Long id);

    int deletes(@Param("ids") List<Long> ids);

    int create(SbElFeeConfig record);

    SbElFeeConfig findById(Long id);

    SbElFeeConfig findByYear(String year);

    int update(SbElFeeConfig record);

    List<SbElFeeConfig> list();
}