package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElFeeQuarterT;
import org.springframework.stereotype.Repository;

@Repository
public interface ElFeeQuarterTMapper {
    int delete(Long id);

    int insert(ElFeeQuarterT record);

    ElFeeQuarterT findById(Long id);

    int update(ElFeeQuarterT record);

}