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
@RpcProvider(version = "0.0.1")
public class SbEquipmentTServiceImpl implements SbEquipmentTService {

    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;

    public void createSbEquipmentT(SbEquipmentT sbEquipmentT) {
        sbEquipmentTMapper.insert(sbEquipmentT);
    }

    public void updateSbEquipmentT(SbEquipmentT sbEquipmentT) {
        sbEquipmentTMapper.updateByPrimaryKey(sbEquipmentT);
    }

    public SbEquipmentT findSbEquipmentByKey(Long id) {
        return sbEquipmentTMapper.selectByPrimaryKey(id);
    }

    public void deleteSbEquipmentByKey(Long id) {
        sbEquipmentTMapper.deleteByPrimaryKey(id);
    }

    public PageInfo<SbEquipmentT> listAll(SbEquipmentT equipmentT, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SbEquipmentT> list = sbEquipmentTMapper.listAll(equipmentT);
        PageInfo<SbEquipmentT> pageInfo = new PageInfo<SbEquipmentT>(list);
        return pageInfo;
    }
}
