package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;


public interface ZjDepreciationCostReportService {

    /**
     * @method 创建综机设备使用交接单
     * @param zjDepreciationCostReport
     * @return
     */
    Boolean create(ZjDepreciationCostReport zjDepreciationCostReport);

}
