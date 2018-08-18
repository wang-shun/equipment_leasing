package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElPlanUseMapper;
import com.yankuang.equipment.equipment.model.ElPlanUse;
import com.yankuang.equipment.equipment.service.ElPlanUseService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RpcProvider
@Service
public class ElPlanUseServiceImpl implements ElPlanUseService{

    @Autowired
    ElPlanUseMapper elPlanUseMapper;
    public Boolean update(ElPlanUse elPlanUse){
        return elPlanUseMapper.update(elPlanUse) >=0;
    }
}
