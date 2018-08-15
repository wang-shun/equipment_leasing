package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbEquipmentT;

public interface SbEquipmentTService {

    /**
     * 创建通用设备
     * @param sbEquipmentT
     */
    public void create(SbEquipmentT sbEquipmentT);

    /**
     * 更新通用设备
     * @param sbEquipmentT
     */
    public void update(SbEquipmentT sbEquipmentT);

    /**
     * 根据主键ID查询通用设备
     * @param id
     * @return
     */
    public SbEquipmentT findById(Long id);

    /**
     * 根据主键删除通用设备
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 分页查询通用设备
     * @param code
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SbEquipmentT> list(String code,String name, int pageNum, int pageSize);
}
