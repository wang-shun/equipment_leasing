package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElFeeQuarterT;

import java.util.List;

public interface ElFeeQuarterTService {
    List<ElFeeQuarterT> findList(ElFeeQuarterT elFeeQuarterT);

    boolean createBatch(List<ElFeeQuarterT> list);
}
