package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElEquipmentGroupConfigMapper;
import com.yankuang.equipment.equipment.model.ElEquipmentGroupConfig;
import com.yankuang.equipment.equipment.service.ElEquipmentGroupConfigService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zyy
 * @Date: 2018-10-11 15:30
 * @Description:
 */
@Service
@RpcProvider
public class ElEquipmentGroupConfigServiceImpl implements ElEquipmentGroupConfigService {

    @Autowired
    ElEquipmentGroupConfigMapper elEquipmentGroupConfigMapper;

    public boolean createBatch(List<ElEquipmentGroupConfig> elEquipmentGroupConfigs) {
        return elEquipmentGroupConfigMapper.createBatch(elEquipmentGroupConfigs) > 0;
    }

    public List<Map<String, Object>> findByCondition(ElEquipmentGroupConfig elEquipmentGroupConfig) {

        List<ElEquipmentGroupConfig> groupConfigs = elEquipmentGroupConfigMapper.list(elEquipmentGroupConfig);
        if (groupConfigs == null || groupConfigs.size() <= 0) return null;
        Map<String, List<ElEquipmentGroupConfig>> groupConfigMap = getGroupConfigMap(groupConfigs);
        Map<String, Object> configMap = new HashMap<String, Object>();
        List<Map<String, Object>> configMaps = new ArrayList<Map<String, Object>>();
        for (String configYear : groupConfigMap.keySet()) {
            if (groupConfigMap.get(configYear) == null || groupConfigMap.get(configYear).size() <= 0) continue;

            List<Map<String, Object>> typeMaps = new ArrayList<Map<String, Object>>();
            for (ElEquipmentGroupConfig config : groupConfigMap.get(configYear)) {
                Map<String, Object> typeMap = new HashMap<String, Object>();
                if (config == null) continue;
                if (!StringUtils.isEmpty(config.getSmallTypeCode())) {
                    typeMap.put("typeName", config.getSmallTypeName());
                    typeMap.put("typeCode", config.getSmallTypeCode());
                } else if(!StringUtils.isEmpty(config.getMiddleTypeCode())) {
                    typeMap.put("typeName", config.getMiddleTypeName());
                    typeMap.put("typeCode", config.getMiddleTypeCode());
                } else if (!StringUtils.isEmpty(config.getBigTypeCode())) {
                    typeMap.put("typeName", config.getBigTypeName());
                    typeMap.put("typeCode", config.getBigTypeCode());
                }
                if (StringUtils.isEmpty(typeMap.get("typeName"))) continue;
                typeMap.put("id", config.getId());
                typeMaps.add(typeMap);
            }
            if (typeMaps.size() <= 0) continue;
            configMap.put("typeList", typeMaps);
            configMap.put("configYear", configYear);
            // TODO 资产公司暂时写死，需求变更时重写
            configMap.put("assetCode", "1180");
            configMap.put("assetName", "兖州煤业股份有限公司");
            configMaps.add(configMap);
        }

        return configMaps;
    }

    public boolean delete(Long id) {
        return elEquipmentGroupConfigMapper.delete(id) > 0;
    }

    public ElEquipmentGroupConfig findById(Long id) {
        return elEquipmentGroupConfigMapper.findById(id);
    }

    public Map<String, Object> findByIds(String[] idsI) {
        Map<String, Object> configMap = new HashMap<String, Object>();
        List<ElEquipmentGroupConfig> groupConfigs = new ArrayList<ElEquipmentGroupConfig>();
        for (String id : idsI) {
            ElEquipmentGroupConfig groupConfig = elEquipmentGroupConfigMapper.findById(Long.valueOf(id));
            if (groupConfig != null) groupConfigs.add(groupConfig);
        }
        if (groupConfigs == null || groupConfigs.size() <= 0 || groupConfigs.get(0) == null) return null;
        configMap.put("configYear", groupConfigs.get(0).getConfigYear());
        // TODO 资产公司暂时写死，需求变更时重写
        configMap.put("assetCode", "1180");
        configMap.put("assetName", "兖州煤业股份有限公司");
        configMap.put("equipmentGroup", groupConfigs);
        return configMap;
    }

    public boolean update(ElEquipmentGroupConfig groupConfigs) {
        return elEquipmentGroupConfigMapper.update(groupConfigs) >= 0;
    }

    public Map<String, List<ElEquipmentGroupConfig>> getGroupConfigMap(List<ElEquipmentGroupConfig> groupConfigs) {
        Map<String, List<ElEquipmentGroupConfig>> groupConfigMap = new HashMap<String, List<ElEquipmentGroupConfig>>();
        for (ElEquipmentGroupConfig groupConfig : groupConfigs) {
            if (groupConfig == null) continue;
            List<ElEquipmentGroupConfig> tempList = groupConfigMap.get(groupConfig.getConfigYear());
            if (tempList == null) {
                tempList = new ArrayList<ElEquipmentGroupConfig>();
                tempList.add(groupConfig);
                groupConfigMap.put(groupConfig.getConfigYear(), tempList);
            } else {
                tempList.add(groupConfig);
            }
        }
        return groupConfigMap;
    }
}
