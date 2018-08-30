package com.yankuang.equipment.equipment.service;

import java.util.Date;

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

    /**
     * 根据设备租赁的领用ID、退租ID、设备ID 及 开始日期、结束日期 来计算设备租赁天数
     * @param useId
     * @param backId
     * @param equipmentId
     * @param startDate
     * @param endDate
     * @return
     */
    public Long CalEquipmentElDays(Long useId, Long backId, Long equipmentId, Date startDate, Date endDate);

    /**
     * 通用设备根据设备租赁的领用ID、设备ID 计算设备新度系数调节费
     * @param useId
     * @param equipmentId
     * @return
     */
    public Double CalDayElFeeA3T_rate(Long useId, Long equipmentId);

    /**
     * 综机设备根据设备租赁的领用ID、设备ID 计算设备新度系数调节费
     * @param useId
     * @param equipmentId
     * @return
     */
    public Double CalDayElFeeA3Z_rate(Long useId, Long equipmentId);

}
