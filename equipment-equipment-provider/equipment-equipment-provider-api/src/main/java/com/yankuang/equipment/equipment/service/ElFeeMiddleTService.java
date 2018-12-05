package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElFeeMiddleT;

import java.util.List;

public interface ElFeeMiddleTService {
    List<ElFeeMiddleT> findElFeeMiddleTs(ElFeeMiddleT elFeeMiddleT, Integer pageNum, Integer pageSize);

    boolean createBatch(List<ElFeeMiddleT> list);

    ElFeeMiddleT findTotal(ElFeeMiddleT elFeeMiddleT);
}
