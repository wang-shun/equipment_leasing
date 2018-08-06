package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.SbTypeMapper;
import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.service.SbTypeService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider(version = "0.0.1")
public class SbTypeServiceImpl implements SbTypeService {

    @Autowired
    private SbTypeMapper sbTypeMapper;

    public List<SbType> listSbTypes() {
        return sbTypeMapper.selectAllSbTypes();
    }

    public void createSbType(SbType record){
        sbTypeMapper.insert(record);
    }

    public void deleteSbTypeByKey(Long id) {
        sbTypeMapper.deleteByPrimaryKey(id);
    }
}
