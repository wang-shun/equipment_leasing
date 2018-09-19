package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthItem;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ZjDepreciationCostReportItemService {

    /**
     * @method 创建综机设备使用交接单
     * @param zjDepreciationCostReportItem
     * @return
     */
    Boolean create(ZjDepreciationCostReportItem zjDepreciationCostReportItem);

//    List<ZjDepreciationCostReportItem> listHomeOffice(Long id,String deptType);



}
