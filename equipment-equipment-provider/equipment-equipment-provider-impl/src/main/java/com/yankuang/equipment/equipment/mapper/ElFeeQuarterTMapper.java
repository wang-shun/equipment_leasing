package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElFeeQuarterT;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElFeeQuarterTMapper {
    int delete(Long id);

    int insertBatch(List<ElFeeQuarterT> list);

    ElFeeQuarterT findById(Long id);

    int update(ElFeeQuarterT record);

    int history(ElFeeQuarterT record);

    List<ElFeeQuarterT> list(ElFeeQuarterT elFeeQuarterT);
}