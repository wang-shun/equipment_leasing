package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbEquipmentT;

public interface SbEquipmentTService {

    /**
     * 创建通用设备
     * @param sbEquipmentT
     */
    public void createSbEquipmentT(SbEquipmentT sbEquipmentT);

    /**
     * 更新通用设备
     * @param sbEquipmentT
     */
    public void updateSbEquipmentT(SbEquipmentT sbEquipmentT);

    /**
     * 根据主键ID查询通用设备
     * @param id
     * @return
     */
    public SbEquipmentT findSbEquipmentByKey(Long id);

    /**
     * 根据主键删除通用设备
     * @param id
     */
    public void deleteSbEquipmentByKey(Long id);

    /**
     * 分页查询通用设备
     * @param equipmentT
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SbEquipmentT> listAll(SbEquipmentT equipmentT, int pageNum, int pageSize);
}
