package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ReportEquipmentZMonth;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEquipmentZMonthMapper {

    int insert(ReportEquipmentZMonth record);

    ReportEquipmentZMonth findByYear(String year);
}