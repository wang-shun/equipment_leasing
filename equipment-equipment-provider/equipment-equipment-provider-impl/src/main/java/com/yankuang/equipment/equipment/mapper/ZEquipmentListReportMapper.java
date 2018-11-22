package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ListZReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZEquipmentListReportMapper {

    int create(ListZReport listZReport);

    List<ListZReport> list(ListZReport listZReport);

    int find(DtkList dtkList);

    int update(ListZReport listZReport);
}