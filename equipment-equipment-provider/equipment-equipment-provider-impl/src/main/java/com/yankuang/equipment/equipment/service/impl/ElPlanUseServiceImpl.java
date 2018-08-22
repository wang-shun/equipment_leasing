package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElPlanUseMapper;
import com.yankuang.equipment.equipment.model.ElPlanUse;
import com.yankuang.equipment.equipment.service.ElPlanUseService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class ElPlanUseServiceImpl implements ElPlanUseService {

    @Autowired
    ElPlanUseMapper elPlanUseMapper;

    public Integer create(ElPlanUse elPlanUse) {
        return elPlanUseMapper.insert(elPlanUse);
    }

    public List<ElPlanUse> findElPlanUse(ElPlanUse elPlanUse) {

        return elPlanUseMapper.findByCondition(elPlanUse);
    }

    public Boolean update(ElPlanUse elPlanUse){
        return elPlanUseMapper.update(elPlanUse) >=0;
    }
}
