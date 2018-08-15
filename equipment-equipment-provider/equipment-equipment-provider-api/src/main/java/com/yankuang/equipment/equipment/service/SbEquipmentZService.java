package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbEquipmentZ;

public interface SbEquipmentZService {

    /**
     * 创建综机设备
     * @param sbEquipmentZ
     */
    public void create(SbEquipmentZ sbEquipmentZ);

    /**
     * 更新综机设备
     * @param sbEquipmentZ
     */
    public void update(SbEquipmentZ sbEquipmentZ);

    /**
     * 根据主键ID查询综机设备
     * @param id
     * @return
     */
    public SbEquipmentZ findById(Long id);

    /**
     * 根据主键删除综机设备
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 分页查询综机设备
     * @param code
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SbEquipmentZ> list(String code, String name, int pageNum, int pageSize);
}
