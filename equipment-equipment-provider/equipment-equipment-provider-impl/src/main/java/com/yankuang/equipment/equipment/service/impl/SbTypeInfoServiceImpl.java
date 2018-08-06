package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.SbTypeInfoMapper;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeInfoService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider(version = "0.0.1")
public class SbTypeInfoServiceImpl implements SbTypeInfoService {

    @Autowired
    private SbTypeInfoMapper sbTypeInfoMapper;

    public SbTypeInfo selectByPrimaryKey(Long id) {
        return sbTypeInfoMapper.selectByPrimaryKey(id);
    }

    public void insertSbTypeInfo(SbTypeInfo record){
        sbTypeInfoMapper.insert(record);
    }

    public void updateSbTypeInfo(SbTypeInfo record){
        sbTypeInfoMapper.updateByPrimaryKey(record);
    }

}
