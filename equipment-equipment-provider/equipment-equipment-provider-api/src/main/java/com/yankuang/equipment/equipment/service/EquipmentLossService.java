package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.EquipmentLoss;

public interface EquipmentLossService {

    boolean create(EquipmentLoss equipmentLoss);

    PageInfo<EquipmentLoss> list(Integer page, Integer size, EquipmentLoss equipmentLoss);
}
