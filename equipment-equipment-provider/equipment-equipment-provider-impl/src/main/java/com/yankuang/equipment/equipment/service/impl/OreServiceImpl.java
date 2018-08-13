package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.OreMapper;
import com.yankuang.equipment.equipment.model.ElOre;
import com.yankuang.equipment.equipment.service.OreService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by boms on 2018/8/13
 */

@Service
@RpcProvider
public class OreServiceImpl implements OreService{
    @Autowired
    OreMapper oreMapper;

    public Boolean create(ElOre elOre){
        return oreMapper.create(elOre);
    }
}
