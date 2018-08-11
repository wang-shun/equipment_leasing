package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.SbTypeInfo;

public interface SbTypeInfoService {
    /**
     * 根据id查询设备类型信息
     * @param id
     * @return
     */
    public SbTypeInfo findById(Long id);

    /**
     * 添加设备类型信息
     * @param record
     */
    public void create(SbTypeInfo record);

    /**
     * 更新设备类型信息
     * @param record
     */
    public void update(SbTypeInfo record);
}
