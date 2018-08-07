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

    /**
     * 根据父编码、级别查询设备类型
     * @param pcode
     * @param level
     * @return
     */
    public List<SbType> listSbTypesByPcodeOrLevel(String pcode,int level);
}
