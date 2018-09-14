package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ListZReport;

import java.util.List;

public interface ZEquipmentListReportMapper {

    int create(ListZReport listZReport);

    List<ListZReport> list(ListZReport listZReport);
}