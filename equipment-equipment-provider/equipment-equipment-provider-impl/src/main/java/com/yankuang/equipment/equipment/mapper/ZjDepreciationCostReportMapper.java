package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ZjDepreciationCostReportMapper {

    Integer history(@Param("yearTime") String yearMonthTime,@Param("monthTime") String month,@Param("assetComp") String assetComp);

    Integer create(ZjDepreciationCostReport zjDepreciationCostReport);

    Integer findCostRepairList(@Param("yearTime") String yearMonthTime,@Param("monthTime") String month,@Param("assetComp") String assetComp);

    ZjDepreciationCostReport list(Map zjCostRepairMap);
}
