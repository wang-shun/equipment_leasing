package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ZjxlReport;

import java.util.List;

public interface ZjxlReportService {
    /**
     * 保存折旧修理费清单
     * @param zjxlReport
     * @return
     */
    Boolean create(ZjxlReport zjxlReport);

    /**
     * 查询综机折旧修理费
     * @param zjxlReport
     * @return
     */
    List<ZjxlReport> list(ZjxlReport zjxlReport);
}
