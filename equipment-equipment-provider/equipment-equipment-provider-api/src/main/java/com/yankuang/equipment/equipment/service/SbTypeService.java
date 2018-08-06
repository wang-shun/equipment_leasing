package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.SbType;

import java.util.List;

public interface SbTypeService {
    /**
     * 查询设备类型
     * @return
     */
    public List<SbType> listSbTypes();

    /**
     * 添加设备类型
     * @param record
     */
    public void createSbType(SbType record);

    /**
     * 根据id删除设备类型
     * @param id
     */
    public void deleteSbTypeByKey(Long id);
}
