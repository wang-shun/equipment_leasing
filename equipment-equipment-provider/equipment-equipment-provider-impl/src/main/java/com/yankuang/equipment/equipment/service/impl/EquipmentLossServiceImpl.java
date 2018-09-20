package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.EquipmentLossMapper;
import com.yankuang.equipment.equipment.model.EquipmentLoss;
import com.yankuang.equipment.equipment.service.EquipmentLossService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class EquipmentLossServiceImpl implements EquipmentLossService {
    @Autowired
    EquipmentLossMapper equipmentLossMapper;

    public boolean create(EquipmentLoss equipmentLoss){

        if (equipmentLossMapper.create(equipmentLoss) > 0){
            return true;
        }

        return false;
    }
    public List<EquipmentLoss> list(EquipmentLoss equipmentLoss){
        return equipmentLossMapper.list(equipmentLoss);
    }
}
