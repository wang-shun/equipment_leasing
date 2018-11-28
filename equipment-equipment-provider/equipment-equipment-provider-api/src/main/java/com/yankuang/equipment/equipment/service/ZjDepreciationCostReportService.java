package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface ZjDepreciationCostReportService {


    int findCostRepairList(@Param("yearTime") String yearMonthTime, @Param("monthTime") String month,@Param("assetComp") String assetComp);

    Boolean create(ZjDepreciationCostReport zjDepreciationCostReport);

    ZjDepreciationCostReport list(Map zjCostRepairMap);

    ZjDepreciationCostReport listzjxl(ZjDepreciationCostReport zjxlReport);
    /**
     * 汇总各矿上月的综机租赁费
     */
    public int creatDepCostReport();


}
