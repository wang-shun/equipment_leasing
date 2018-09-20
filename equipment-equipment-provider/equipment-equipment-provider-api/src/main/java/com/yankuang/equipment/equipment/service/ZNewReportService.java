package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ZNewReport;

import java.util.List;

public interface ZNewReportService {
    /**
     * 保存新设备清单数据
     * @param zNewReport
     * @return
     */
    boolean create(ZNewReport zNewReport);

    /**
     * 查询新度系数调节清单
     * @param zNewReport
     * @return
     */
    List<ListZReport> select(ZNewReport zNewReport);

    /**
     *
     * @param zNewReport
     * @return
     */
    List<ZNewReport> list(ZNewReport zNewReport);
}
