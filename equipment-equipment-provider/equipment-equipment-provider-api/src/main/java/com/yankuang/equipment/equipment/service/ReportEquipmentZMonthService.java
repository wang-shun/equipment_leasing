package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ReportEquipmentZMonth;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthItem;
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

    /**
     * 汇总各矿上月的综机租赁费
     */
    public int CalReportEquipmentZMonth();

    /**
     * 根据ID更新报表备注信息
     * @param reportEquipmentZMonth
     */
    public int updateRemarkById(ReportEquipmentZMonth reportEquipmentZMonth);

    /**
     * 根据ID更新年度计划值
     * @param reportEquipmentZMonthItem
     */
    public int updateYearPlanValById(ReportEquipmentZMonthItem reportEquipmentZMonthItem);
}
