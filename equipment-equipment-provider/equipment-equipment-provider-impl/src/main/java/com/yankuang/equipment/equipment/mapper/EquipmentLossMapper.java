package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.EquipmentLoss;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentLossMapper {
    /**
     * 保存新度系数调节费清单
     * @param equipmentLoss
     * @return
     */
    int create(EquipmentLoss equipmentLoss);

    /**
     * 查询新度系数调节清单
     * @param equipmentLoss
     * @return
     */
    List<EquipmentLoss> list(EquipmentLoss equipmentLoss);
}
