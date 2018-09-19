package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.*;
import com.yankuang.equipment.equipment.model.ElPlanUse;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.ElPlanUseService;
import com.yankuang.equipment.equipment.service.SbTypeService;
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

    @Autowired
    SbTypeMapper sbTypeMapper;

    @Autowired
    SbPositionMapper sbPositionMapper;

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
        if (elPlanUses != null && elPlanUses.size() >0 ) {
            for (ElPlanUse elPlanUse : elPlanUses) {
                if (sbTypeMapper.findByCode(elPlanUse.getSmallTypeCode()) == null){
                    continue;
                }
                elPlanUse.setSmallTypeName(sbTypeMapper.findByCode(elPlanUse.getSmallTypeCode()).getName());
                elPlanUse.setMiddleTypeName(sbTypeMapper.findByCode(elPlanUse.getMiddleTypeCode()).getName());
                elPlanUse.setBigTypeName(sbTypeMapper.findByCode(elPlanUse.getBigTypeCode()).getName());
                if ("1".equals(elPlanUse.getEquipmentType())) {
                    if (sbEquipmentTMapper.findById(elPlanUse.getEquipmentId()) == null){
                        continue;
                    }
                    elPlanUse.setTechCode(sbEquipmentTMapper.findById(elPlanUse.getEquipmentId()).getTechCode());
                    elPlanUse.setAssetCode(sbEquipmentTMapper.findById(elPlanUse.getEquipmentId()).getAssetCode());
                }
                if ("2".equals(elPlanUse.getEquipmentType())){
                    if(sbEquipmentZMapper.findById(elPlanUse.getEquipmentId()) == null){
                        continue;
                    }
                    elPlanUse.setTechCode(sbEquipmentZMapper.findById(elPlanUse.getEquipmentId()).getTechCode());
                    elPlanUse.setAssetCode(sbEquipmentZMapper.findById(elPlanUse.getEquipmentId()).getAssetCode());
                }
            }
        }
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
