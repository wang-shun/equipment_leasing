package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbEquipmentZ;

import java.util.List;

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
     * 根据识别码查询设备
     * @param code
     * @return
     */
    public SbEquipmentZ findByCode(String code);

    /**
     * 根据主键删除综机设备
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 分页查询综机设备
     * @param sbEquipmentZ
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SbEquipmentZ> list(SbEquipmentZ sbEquipmentZ, int pageNum, int pageSize);

    /**
     * 批量删除综机设备
     * @param ids
     */
    public void deletes(List<Long> ids);
}
