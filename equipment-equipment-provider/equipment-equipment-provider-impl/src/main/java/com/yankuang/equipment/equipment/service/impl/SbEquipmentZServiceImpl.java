package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.SbEquipmentZMapper;
import com.yankuang.equipment.equipment.model.SbEquipmentZ;
import com.yankuang.equipment.equipment.service.SbEquipmentZService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class SbEquipmentZServiceImpl implements SbEquipmentZService {

    @Autowired
    SbEquipmentZMapper sbEquipmentZMapper;

    public void create(SbEquipmentZ sbEquipmentZ) {
        sbEquipmentZMapper.create(sbEquipmentZ);
    }

    public void update(SbEquipmentZ sbEquipmentZ) {
        sbEquipmentZMapper.update(sbEquipmentZ);
    }

    public SbEquipmentZ findById(Long id) {
        return sbEquipmentZMapper.findById(id);
    }

    public SbEquipmentZ findByCode(String code){
        return sbEquipmentZMapper.findByCode(code);
    }

    public void deleteById(Long id) {
        sbEquipmentZMapper.deleteById(id);
    }

    public PageInfo<SbEquipmentZ> list(SbEquipmentZ sbEquipmentZ, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SbEquipmentZ> list = sbEquipmentZMapper.list(sbEquipmentZ);
        PageInfo<SbEquipmentZ> pageInfo = new PageInfo<SbEquipmentZ>(list);
        return pageInfo;
    }

    public void deletes(List<Long> ids){
        sbEquipmentZMapper.deletes(ids);
    }
}
