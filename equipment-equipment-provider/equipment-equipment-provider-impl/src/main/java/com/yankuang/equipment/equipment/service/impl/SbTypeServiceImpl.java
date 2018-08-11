package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.SbTypeInfoMapper;
import com.yankuang.equipment.equipment.mapper.SbTypeMapper;
import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class SbTypeServiceImpl implements SbTypeService {

    @Autowired
    private SbTypeMapper sbTypeMapper;

    @Autowired
    private SbTypeInfoMapper sbTypeInfoMapper;

    public List<SbType> list() {
        return sbTypeMapper.list();
    }

    public void create(SbType record, SbTypeInfo sbTypeInfo){
        sbTypeMapper.create(record);
        sbTypeInfo.setTypeId(record.getId());
        sbTypeInfoMapper.create(sbTypeInfo);
    }

    public void deleteById(Long id) {
        sbTypeMapper.deleteById(id);
    }

    public List<SbType> listByPcodeOrLevel(String pcode, int level) {
        return sbTypeMapper.listByPcodeOrLevel(pcode,level);
    }
}
