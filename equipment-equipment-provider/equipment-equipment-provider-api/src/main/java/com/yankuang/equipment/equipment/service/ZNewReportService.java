package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
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
    PageInfo<ListZReport> select(Integer page, Integer size, ZNewReport zNewReport);

    /**
     *
     * @param zNewReport
     * @return
     */
    PageInfo<ZNewReport> list(Integer page,Integer size,ZNewReport zNewReport);
}
