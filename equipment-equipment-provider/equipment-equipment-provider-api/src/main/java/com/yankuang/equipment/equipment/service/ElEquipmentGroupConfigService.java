package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElEquipmentGroupConfig;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zyy
 * @Date: 2018-10-11 15:28
 * @Description:
 */
public interface ElEquipmentGroupConfigService {

    boolean createBatch(List<ElEquipmentGroupConfig> elEquipmentGroupConfigs);

    List<Map<String, Object>> findByCondition(ElEquipmentGroupConfig elEquipmentGroupConfig);

    boolean delete(Long id);

    ElEquipmentGroupConfig findById(Long id);

    boolean update(ElEquipmentGroupConfig groupConfigs);

    Map<String,Object> findByIds(String[] idsI);
}
