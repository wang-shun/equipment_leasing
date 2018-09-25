package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElFeePositionT;

import java.util.List;

public interface ElFeePositionTService {
    List<ElFeePositionT> list(ElFeePositionT elFeePositionT);

    boolean createBatch(List<ElFeePositionT> list);
}
