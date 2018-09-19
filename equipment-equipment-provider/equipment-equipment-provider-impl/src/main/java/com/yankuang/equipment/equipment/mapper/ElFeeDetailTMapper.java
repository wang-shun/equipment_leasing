package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElFeeDetailTMapper {
    int delete(Long id);

    ElFeeDetailT findById(Long id);

    int update(ElFeeDetailT record);

    boolean insertBatch(List<ElFeeDetailT> list);

    List<ElFeeDetailT> list(ElFeeDetailT elFeeDetailT);
}