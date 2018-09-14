package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ListZReportItem;

import java.util.List;

public interface ZEquipmentListReportItemMapper {

    Integer create(ListZReportItem listZReportitem);

    List<ListZReportItem> list(ListZReportItem listZReportItem);
}