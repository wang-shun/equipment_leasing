package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElFeeMiddleTMapper {
    int delete(Long id);

    int insert(ElFeeMiddleT record);

    ElFeeMiddleT findById(Long id);

    int update(ElFeeMiddleT record);

    List<ElFeeMiddleT> list(ElFeeMiddleT elFeeMiddleT);

    int createBatch(List<ElFeeMiddleT> list);
}