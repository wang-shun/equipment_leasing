package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ListZReportItem;

import java.util.List;

public interface ZEquipmentReportService {

    /**
     * 导出时创建清单
     * @param listZReport
     * @return
     */
    Boolean create(ListZReport listZReport, List<ListZReportItem> listZReportItems);
}
