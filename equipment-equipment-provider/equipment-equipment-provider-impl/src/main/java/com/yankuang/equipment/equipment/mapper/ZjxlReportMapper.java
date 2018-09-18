package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjxlReport;
import org.springframework.stereotype.Repository;

@Repository
public interface ZjxlReportMapper {

    int create(ZjxlReport record);
}
