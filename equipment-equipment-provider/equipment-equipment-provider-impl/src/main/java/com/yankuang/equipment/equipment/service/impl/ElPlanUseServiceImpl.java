package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ElPlanUseMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentTMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentZMapper;
import com.yankuang.equipment.equipment.model.ElPlanUse;
import com.yankuang.equipment.equipment.service.ElPlanUseService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ElPlanUseServiceImpl implements ElPlanUseService {

    @Autowired
    ElPlanUseMapper elPlanUseMapper;

    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;

    @Autowired
    SbEquipmentZMapper sbEquipmentZMapper;

    public Integer create(ElPlanUse elPlanUse) {
        return elPlanUseMapper.insert(elPlanUse);
    }

    public List<ElPlanUse> findElPlanUse(ElPlanUse elPlanUse) {

        return elPlanUseMapper.findByCondition(elPlanUse);
    }

    public Boolean update(ElPlanUse elPlanUse){
        return elPlanUseMapper.update(elPlanUse) >=0;
    }

    public PageInfo<ElPlanUse> list(Integer page, Integer size, Map elPlanUseMap){
        PageHelper.startPage(page,size);
        List<ElPlanUse> elPlanUses = elPlanUseMapper.list(elPlanUseMap);
        PageInfo<ElPlanUse> pageInfo = new PageInfo<ElPlanUse>(elPlanUses);
        return pageInfo;
    }

    public boolean unbindSbT(){
        return sbEquipmentTMapper.updateStateCodeByMonth() >= 0;
    }

    public boolean unbindSbZ(){
        return sbEquipmentZMapper.updateStateCodeByMonth() >= 0;
    }
}
