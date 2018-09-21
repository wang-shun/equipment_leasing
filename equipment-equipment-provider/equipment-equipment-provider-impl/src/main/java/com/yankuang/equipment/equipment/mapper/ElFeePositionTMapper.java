package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElFeePositionT;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElFeePositionTMapper {
    int delete(Long id);

    int insert(ElFeePositionT record);

    ElFeePositionT findById(Long id);

    int update(ElFeePositionT record);

    int history();

    List<ElFeePositionT> list(ElFeePositionT elFeePositionT);
}