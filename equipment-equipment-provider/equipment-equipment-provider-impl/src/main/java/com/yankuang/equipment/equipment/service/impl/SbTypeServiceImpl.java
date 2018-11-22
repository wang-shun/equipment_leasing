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
        List<SbType> typeOnes = sbTypeMapper.listByPcodeOrLevel("",1);
        if(typeOnes!=null){
            for(int i=0;i<typeOnes.size();i++){
                SbType typeOne = typeOnes.get(i);
                List<SbType> typeTwos = sbTypeMapper.listByPcodeOrLevel(typeOne.getCode(),2);
                if(typeTwos!=null){
                    typeOne.setChildren(typeTwos);
                    for(int j=0;j<typeTwos.size();j++){
                        SbType typeTwo = typeTwos.get(j);
                        List<SbType> typeThrees = sbTypeMapper.listByPcodeOrLevel(typeTwo.getCode(),3);
                        typeTwo.setChildren(typeThrees);
                    }
                }
            }
        }

        return typeOnes;
    }

    public void create(SbType record, SbTypeInfo sbTypeInfo){
        int num = Integer.parseInt(sbTypeMapper.create(record)+"");
        sbTypeInfo.setTypeId(record.getId());
        sbTypeInfoMapper.create(sbTypeInfo);
    }

    public SbType findByCode(String code){
        return sbTypeMapper.findByCode(code);
    }

    public void deleteById(Long id) {
        SbType sbType = sbTypeMapper.findById(id);
        if(sbType != null){
            List<SbType> sbTypes = sbTypeMapper.listByPcodeOrLevel(sbType.getCode(),null);
            if(sbTypes!=null && sbTypes.size()>0){
                for(int i=0;i<sbTypes.size();i++){
                    SbType sbType1 = sbTypes.get(i);
                    sbTypeMapper.deleteByPcode(sbType1.getCode());
                    sbTypeInfoMapper.deleteByTypeId(sbType1.getId());
                    sbTypeMapper.deleteById(sbType1.getId());
                }
            }
            sbTypeInfoMapper.deleteByTypeId(sbType.getId());
            sbTypeMapper.deleteById(sbType.getId());
        }
    }

    public List<SbType> listByPcodeOrLevel(String p_code, int level) {
        return sbTypeMapper.listByPcodeOrLevel(p_code,level);
    }
}
