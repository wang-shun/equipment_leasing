package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.model.SbTypeInfo;

import java.util.List;

public interface SbTypeService {
    /**
     * 查询设备类型
     * @return
     */
    public List<SbType> list();

    /**
     * 添加设备类型、类型信息
     * @param record
     * @param sbTypeInfo
     */
    public void create(SbType record, SbTypeInfo sbTypeInfo);

    public SbType findByCode(String code);

    /**
     * 根据id删除设备类型
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 根据父编码、级别查询设备类型
     * @param p_code
     * @param level
     * @return
     */
    public List<SbType> listByPcodeOrLevel(String p_code,int level);
}
