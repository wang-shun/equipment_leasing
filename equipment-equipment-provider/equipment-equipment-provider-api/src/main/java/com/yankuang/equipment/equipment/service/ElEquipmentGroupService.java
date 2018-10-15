package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElEquipmentGroup;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-08 11:37
 * @Description:
 */
public interface ElEquipmentGroupService {

    /**
     * 编辑更新设备组
     * @param elEquipmentGroup
     * @return
     */
    boolean update(ElEquipmentGroup elEquipmentGroup);

    List<ElEquipmentGroup> findByCondition(ElEquipmentGroup groupI);

    boolean create(ElEquipmentGroup group);
}
