package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ListZReportItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZEquipmentListReportItemMapper {

    Integer create(ListZReportItem listZReportitem);

    List<ListZReportItem> list(ListZReportItem listZReportItem);
}