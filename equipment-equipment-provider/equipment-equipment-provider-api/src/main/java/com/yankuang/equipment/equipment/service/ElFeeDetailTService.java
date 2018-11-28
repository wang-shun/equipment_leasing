package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElFeeDetailT;

import java.util.List;

public interface ElFeeDetailTService {

    List<ElFeeDetailT> findElFeeDetailTs(ElFeeDetailT elFeeDetailT, Integer pageNum, Integer pageSize);

    /**
     * 批量新增通用设备租赁明细
     * @param list
     * @return
     */
    boolean createBatch(List<ElFeeDetailT> list);
}
