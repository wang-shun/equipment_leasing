package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.SbTypeInfoMapper;
import com.yankuang.equipment.equipment.mapper.SbTypeMapper;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeInfoService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class SbTypeInfoServiceImpl implements SbTypeInfoService {

    @Autowired
    private SbTypeMapper sbTypeMapper;

    @Autowired
    private SbTypeInfoMapper sbTypeInfoMapper;

    public SbTypeInfo findById(Long id) {
        return sbTypeInfoMapper.findById(id);
    }

    public void create(SbTypeInfo record){
        sbTypeInfoMapper.create(record);
    }

    public void update(SbTypeInfo record){
        sbTypeMapper.update(record);
        sbTypeInfoMapper.update(record);
    }

}
