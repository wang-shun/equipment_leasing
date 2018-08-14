package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.OreMapper;
import com.yankuang.equipment.equipment.model.ElOre;
import com.yankuang.equipment.equipment.service.OreService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public Boolean update(ElOre elOre){
        return oreMapper.update(elOre);
    }

    @Transactional
    public Boolean delete(Long id){
        return oreMapper.delete(id);
    }

    public Byte findType(Long id){
        return oreMapper.findType(id);
    }

    @Transactional
    public Boolean deleteOre(Long id){
        return oreMapper.deleteOre(id);
    }

    public List<Long> getId(Long id){
        return oreMapper.getId(id);
    }

    public ElOre findById(Long id){
        return oreMapper.findById(id);
    }

    public List<ElOre> findByPId(Long id){
        return oreMapper.findByPId(id);
    }
}
