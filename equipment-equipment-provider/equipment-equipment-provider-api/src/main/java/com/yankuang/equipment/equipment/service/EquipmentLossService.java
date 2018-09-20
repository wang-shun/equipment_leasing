package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.EquipmentLoss;

import java.util.List;

public interface EquipmentLossService {

    boolean create(EquipmentLoss equipmentLoss);

    List<EquipmentLoss> list(EquipmentLoss equipmentLoss);
}
