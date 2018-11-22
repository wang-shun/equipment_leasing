package com.yankuang.equipment.web.dto;

import com.yankuang.equipment.equipment.model.EquipmentLoss;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EquipmentLossDTO implements Serializable {

    private List<EquipmentLoss> equipmentLosses;
}
