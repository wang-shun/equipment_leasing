package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ListZReportItem;

import java.util.List;
import java.util.Map;

public interface ZEquipmentReportService {

    /**
     * 导出时创建清单
     * @param listZReport
     * @return
     */
    Boolean create(ListZReport listZReport, List<ListZReportItem> listZReportItems);

    /**
     * 分页查询列表
     * @param page
     * @param size
     * @param dtkList
     * @return
     */
    Map findByPage(Integer page, Integer size, DtkList dtkList);

    /**
     * 查询是否是历史报表记录
     * @param dtkList
     * @return
     */
    Boolean find(DtkList dtkList);
}
