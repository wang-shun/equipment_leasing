package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ZjDepreciationCostReportMapper {

    Integer create(ZjDepreciationCostReport zjDepreciationCostReport);

    Integer findCostRepairList(String yearMonthTime);

    ZjDepreciationCostReport list(Map zjCostRepairMap);
}
