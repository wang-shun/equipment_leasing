package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ReportEquipmentZMonth;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthSum;

public interface ReportEquipmentZMonthService {

    /**
     * 根据年份查询综机折旧修理费月报(汇总)
     * @param year
     * @return
     */
    public ReportEquipmentZMonth findByYear(String year);

    /**
     * 根据年份查询综机折旧修理费月报(汇总)
     * @param year
     * @return
     */
    public ReportEquipmentZMonthSum findSumByYear(String year);
}
