package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ReportEquipmentZMonth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEquipmentZMonthMapper {

    int insert(ReportEquipmentZMonth record);

    int updateRemarkById(ReportEquipmentZMonth reportEquipmentZMonth);

    ReportEquipmentZMonth findByYear(String year);
}