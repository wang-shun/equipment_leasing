package com.yankuang.equipment.equipment.service;

public interface SbElFeeService {

    /**
     * 通用设备根据设备Id计算设备日租赁费
     * @param equipmentId
     * @return
     */
    public Double CalDayElFeeA1ByEquipmentTId(Long equipmentId);

    /**
     * 综机设备根据设备Id计算设备日租赁费
     * @param equipmentId
     * @return
     */
    public Double CalDayElFeeA1ByEquipmentZId(Long equipmentId);

}
