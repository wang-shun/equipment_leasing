package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;

import java.util.List;
import java.util.Map;


public interface ZjDepreciationCostReportService {


    int findCostRepairList(String yearMonthTime);
    /**
     * @method 创建综机设备使用交接单
     * @param zjDepreciationCostReport
     * @return
     */
    Boolean create(ZjDepreciationCostReport zjDepreciationCostReport);

    ZjDepreciationCostReport list(Map zjCostRepairMap);

}
