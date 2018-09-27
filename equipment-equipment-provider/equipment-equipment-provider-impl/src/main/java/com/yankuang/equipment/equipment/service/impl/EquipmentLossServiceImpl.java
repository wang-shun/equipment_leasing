package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        equipmentLossMapper.update(equipmentLoss);
        if (equipmentLossMapper.create(equipmentLoss) > 0){
            return true;
        }

        return false;
    }
    public PageInfo<EquipmentLoss> list(Integer page,Integer size,EquipmentLoss equipmentLoss){
        PageHelper.startPage(page, size);
        List<EquipmentLoss> maps = equipmentLossMapper.list(equipmentLoss);
        PageInfo<EquipmentLoss> pageInfo = new PageInfo<EquipmentLoss>(maps);
        return pageInfo;
    }
}
