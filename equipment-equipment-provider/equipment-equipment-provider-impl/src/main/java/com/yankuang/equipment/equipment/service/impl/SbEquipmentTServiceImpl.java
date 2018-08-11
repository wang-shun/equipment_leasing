package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.SbEquipmentTMapper;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.SbEquipmentTService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class SbEquipmentTServiceImpl implements SbEquipmentTService {

    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;

    public void create(SbEquipmentT sbEquipmentT) {
        sbEquipmentTMapper.create(sbEquipmentT);
    }

    public void update(SbEquipmentT sbEquipmentT) {
        sbEquipmentTMapper.update(sbEquipmentT);
    }

    public SbEquipmentT findById(Long id) {
        return sbEquipmentTMapper.findById(id);
    }

    public void deleteById(Long id) {
        sbEquipmentTMapper.deleteById(id);
    }

    public PageInfo<SbEquipmentT> list(SbEquipmentT equipmentT, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SbEquipmentT> list = sbEquipmentTMapper.list(equipmentT);
        PageInfo<SbEquipmentT> pageInfo = new PageInfo<SbEquipmentT>(list);
        return pageInfo;
    }
}
